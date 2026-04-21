package com.GuildAdventure.demo.Service;

import com.GuildAdventure.demo.Domain.Model.Aventura.Enums.ClassTypeEnum;
import com.GuildAdventure.demo.Domain.Model.Aventura.Events.AventureiroAlteradoEvent;
import com.GuildAdventure.demo.Domain.Model.Aventura.entities.AventureiroEntity;
import com.GuildAdventure.demo.Domain.Model.Aventura.entities.CompanheiroEntity;
import com.GuildAdventure.demo.Domain.Model.Aventura.entities.MissaoEntity;
import com.GuildAdventure.demo.Dto.*;
import com.GuildAdventure.demo.Exception.AventureiroExceptionService;
import com.GuildAdventure.demo.Exception.ErroCatalogo;
import com.GuildAdventure.demo.Repository.IAventureiroRepository;
import com.GuildAdventure.demo.Repository.IOrganizacaoRepository;
import com.GuildAdventure.demo.Repository.IUsuarioRepository;
import com.GuildAdventure.demo.Service.Validations.AventureiroValidations.IAventureiroValidate;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AventureiroService {

    private final IAventureiroRepository repository;
    private final IOrganizacaoRepository organizacaoRepository;
    private final IUsuarioRepository usuarioRepository;
    private final AventureiroExceptionService exceptionService;
    private final IMapper mapper;
    private final List<IAventureiroValidate> validators;
    private final ApplicationEventPublisher eventPublisher;

    public AventureiroService(IAventureiroRepository repository,
                              IOrganizacaoRepository organizacaoRepository,
                              IUsuarioRepository usuarioRepository,
                              AventureiroExceptionService exceptionService,
                              IMapper mapper,
                              List<IAventureiroValidate> validators,ApplicationEventPublisher eventPublisher) {
        this.repository = repository;
        this.organizacaoRepository = organizacaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.exceptionService = exceptionService;
        this.mapper = mapper;
        this.validators = validators;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public AventureiroResponseSimples salvar(CreateAventureiroRequest request) {
        validators.forEach(v -> v.validar(request));

        AventureiroEntity entity = mapper.toEntity(request);

        entity.setOrganizacao(organizacaoRepository.findById(request.getOrganizacao_id())
                .orElseThrow(() -> new EntityNotFoundException("Organização não encontrada")));

        entity.setUsuarioCadastro(usuarioRepository.findById(request.getUsuario_cadastrado_id())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado")));

        entity.setAtivo(true);
        return mapper.toSimpleResponse(repository.save(entity));
    }


    @Transactional(readOnly = true)
    public Page<AventureiroResponseSimples> buscarParcial(String nome, Pageable pageable) {
        return repository.buscaParcial(nome, pageable).map(mapper::toSimpleResponse);
    }

    @Transactional(readOnly = true)
    public Page<AventureiroResponseSimples> buscarComFiltros(Boolean ativo, ClassTypeEnum classe, Integer nivel, Pageable pageable) {
        return repository.buscarComFiltros(ativo, classe, nivel, pageable).map(mapper::toSimpleResponse);
    }

    @Transactional(readOnly = true)
    public AventureiroResponse obterPerfilCompleto(Long id) {
        AventureiroResponse response = repository.buscarAventureiroCompleto(id);

        if (response == null) {
            throw new EntityNotFoundException("Aventureiro não encontrado com o ID: " + id);
        }

        MissaoEntity ultimaMissao = repository.buscarUltimaMissaoDoAventureiro(id);
        response.setUltimaMissao(ultimaMissao);

        return response;
    }

    @Transactional
    public void alterarStatus(Long id, boolean ativo) {
        AventureiroEntity aventureiro = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro não encontrado."));
        aventureiro.setAtivo(ativo);
        repository.save(aventureiro);
    }

    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Aventureiro não encontrado.");
        }
        repository.deleteById(id);
    }

    @Transactional
    public AventureiroResponseSimples atualizar(Long id, UpdateAventureiroRequest request) {
        AventureiroEntity aventureiro = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro não encontrado"));


        if (request.getNivel() < 1) {
            exceptionService.lancar(ErroCatalogo.NIVEL_DE_AVENTUREIRO_MENOR_QUE_UM);
        }
        if (request.getOrganizacao_id() == null || !organizacaoRepository.existsById(request.getOrganizacao_id())) {
            exceptionService.lancar(ErroCatalogo.ORGANIZACAO_NAO_EXISTE);
        }


        aventureiro.setNome(request.getNome());
        aventureiro.setClasse(ClassTypeEnum.valueOf(request.getClasse().toUpperCase()));
        aventureiro.setNivel(request.getNivel());

        if (aventureiro.getOrganizacao().getId() != request.getOrganizacao_id()) {
            aventureiro.setOrganizacao(organizacaoRepository.findById(request.getOrganizacao_id())
                    .orElseThrow(() -> new EntityNotFoundException("Nova organização não encontrada")));
        }

        eventPublisher.publishEvent(new AventureiroAlteradoEvent(id));

        return mapper.toSimpleResponse(repository.save(aventureiro));
    }

    @Transactional
    public void removerCompanheiro(Long id) {
        AventureiroEntity aventureiro = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro não encontrado."));

        if (aventureiro.getCompanheiro() != null) {
            aventureiro.setCompanheiro(null);
            repository.save(aventureiro);
        }
    }
    @Transactional
    public AventureiroResponse atualizarCompanheiro(Long id, CompanheiroRequest request) {
        AventureiroEntity aventureiro = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro não encontrado."));

        if (request == null) {
            throw new IllegalArgumentException("O corpo da requisição não pode ser vazio.");
        }
        if (request.getNome() != null && request.getNome().length() > 120) {
            throw new IllegalArgumentException("Nome do companheiro deve ter no máximo 120 caracteres.");
        }
        if (request.getLealdade() < 0 || request.getLealdade() > 100) {
            throw new IllegalArgumentException("Lealdade deve estar entre 0 e 100.");
        }

        CompanheiroEntity companheiro = aventureiro.getCompanheiro();
        if (companheiro == null) {
            companheiro = new CompanheiroEntity();
            companheiro.setAventureiro(aventureiro);
        }

        companheiro.setNome(request.getNome());
        companheiro.setTipoDeCompanheiro(com.GuildAdventure.demo.Domain.Model.Aventura.Enums.CompanionTypeEnum.valueOf(request.getTipoDeCompanheiro().toUpperCase()));
        companheiro.setLealdade(request.getLealdade());

        aventureiro.setCompanheiro(companheiro);

        repository.save(aventureiro);

        eventPublisher.publishEvent(new AventureiroAlteradoEvent(id));

        return obterPerfilCompleto(id);
    }
}