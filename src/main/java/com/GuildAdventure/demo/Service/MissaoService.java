package com.GuildAdventure.demo.Service;

import com.GuildAdventure.demo.Domain.Model.Audit.entities.OrganizacoesEntity;
import com.GuildAdventure.demo.Domain.Model.Aventura.Enums.NivelDePerigoTypeEnum;
import com.GuildAdventure.demo.Domain.Model.Aventura.Enums.StatusMissaoEnum;
import com.GuildAdventure.demo.Domain.Model.Aventura.Events.AventureiroAlteradoEvent;
import com.GuildAdventure.demo.Domain.Model.Aventura.entities.AventureiroEntity;
import com.GuildAdventure.demo.Domain.Model.Aventura.entities.MissaoEntity;
import com.GuildAdventure.demo.Domain.Model.Aventura.entities.ParticipacaoMissao;
import com.GuildAdventure.demo.Domain.Model.Aventura.entities.ParticipacaoMissaoId;
import com.GuildAdventure.demo.Dto.*;
import com.GuildAdventure.demo.Repository.IAventureiroRepository;
import com.GuildAdventure.demo.Repository.IMissaoRepository;
import com.GuildAdventure.demo.Repository.IOrganizacaoRepository;
import com.GuildAdventure.demo.Repository.IParticipacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class MissaoService {

    private final IMissaoRepository repository;
    private final IMapper mapper;
    private final IOrganizacaoRepository organizacaoRepository;
    private final IParticipacaoRepository participacaoRepository;
    private final IAventureiroRepository aventureiroRepository;
    private final ApplicationEventPublisher eventPublisher;

    public MissaoService(IMissaoRepository repository, IMapper mapper, IOrganizacaoRepository organizacaoRepository, IParticipacaoRepository participacaoRepository, IAventureiroRepository aventureiroRepository, ApplicationEventPublisher eventPublisher) {
        this.repository = repository;
        this.mapper = mapper;
        this.organizacaoRepository = organizacaoRepository;
        this.participacaoRepository = participacaoRepository;
        this.aventureiroRepository = aventureiroRepository;
        this.eventPublisher = eventPublisher;
    }



    @Transactional(readOnly = true)
    public MissaoResponse buscarDetalhes(Long id) {
        MissaoEntity missao = repository.buscarDetalhesMissao(id)
                .orElseThrow(() -> new EntityNotFoundException("Missão não encontrada com o ID: " + id));


        return mapper.toMissaoResponse(missao);
    }

    @Transactional(readOnly = true)
    public List<MissaoMetricasResponse> gerarRelatorio() {
        return repository.gerarRelatorioMetricas();
    }

    @Transactional
    public MissaoResponse criarMissao(CreateMissaoRequest request) {


        OrganizacoesEntity organizacao = organizacaoRepository.findById(request.organizacaoId())
                .orElseThrow(() -> new EntityNotFoundException("Organização não encontrada"));

        MissaoEntity novaMissao = mapper.toEntity(request, organizacao);

        novaMissao.setStatusMissao(StatusMissaoEnum.EM_ANDAMENTO);


        MissaoEntity missaoSalva = repository.save(novaMissao);


        return mapper.toResponse(missaoSalva);
    }

    @Transactional(readOnly = true)
    public Page<MissaoResponse> buscarPorStatus(StatusMissaoEnum status, Pageable pageable) {
        Page<MissaoEntity> paginaEntidades = repository.buscarPorStatus(status, pageable);


        return paginaEntidades.map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<MissaoResponse> buscarPorNivelPerigo(NivelDePerigoTypeEnum nivelPerigo, Pageable pageable) {
        Page<MissaoEntity> paginaEntidades = repository.buscarPorNivelPerigo(nivelPerigo, pageable);
        return paginaEntidades.map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<MissaoResponse> buscarPorDataCriacao(OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable) {
        if (dataInicio != null && dataFim != null && dataInicio.isAfter(dataFim)) {
            throw new IllegalArgumentException("A data de início não pode ser posterior à data de fim.");
        }

        Page<MissaoEntity> paginaEntidades = repository.buscarPorDataCriacao(dataInicio, dataFim, pageable);
        return paginaEntidades.map(mapper::toResponse);
    }

    @Transactional
    public MissaoResponse adicionarAventureiroNaMissao(Long missaoId, AdicionarAventureiroRequest request) {

        ParticipacaoMissaoId idComposto = new ParticipacaoMissaoId(request.aventureiroId(), missaoId);

        if (participacaoRepository.existsById(idComposto)) {
            throw new IllegalStateException("Este aventureiro já está participando desta missão.");
        }

        MissaoEntity missao = repository.findById(missaoId)
                .orElseThrow(() -> new EntityNotFoundException("Missão não encontrada."));

        AventureiroEntity aventureiro = aventureiroRepository.findById(request.aventureiroId())
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro não encontrado."));

        ParticipacaoMissao participacao = mapper.toParticipacaoEntity(
                request, missao, aventureiro, idComposto
        );

        participacaoRepository.save(participacao);
        eventPublisher.publishEvent(new AventureiroAlteradoEvent(aventureiro.getId()));

        MissaoEntity missaoAtualizada = repository.findById(missaoId)
                .orElseThrow(() -> new EntityNotFoundException("Missão não encontrada após atualização."));

        return mapper.toResponse(missaoAtualizada);
    }

    @Transactional
    public MissaoResponse removerAventureiroDaMissao(Long missaoId, Long aventureiroId) {

        ParticipacaoMissaoId idComposto = new ParticipacaoMissaoId(aventureiroId, missaoId);


        if (!participacaoRepository.existsById(idComposto)) {
            throw new EntityNotFoundException("Registro de participação não encontrado.");
        }


        participacaoRepository.deleteById(idComposto);
        eventPublisher.publishEvent(new AventureiroAlteradoEvent(aventureiroId));


        MissaoEntity missaoAtualizada = repository.findById(missaoId)
                .orElseThrow(() -> new EntityNotFoundException("Missão não encontrada."));

        return mapper.toResponse(missaoAtualizada);
    }

}