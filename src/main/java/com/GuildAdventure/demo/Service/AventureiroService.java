package com.GuildAdventure.demo.Service;

import com.GuildAdventure.demo.Domain.Model.Aventura.entities.AventureiroEntity;
import com.GuildAdventure.demo.Domain.Model.Aventura.entities.CompanheiroEntity;
import com.GuildAdventure.demo.Domain.Model.Aventura.entities.MissaoEntity;
import com.GuildAdventure.demo.Dto.AventureiroResponse;
import com.GuildAdventure.demo.Dto.IMapper;
import com.GuildAdventure.demo.Dto.RankingResponse;
import com.GuildAdventure.demo.Exception.AventureiroExceptionService;
import com.GuildAdventure.demo.Exception.ErroCatalogo;
import com.GuildAdventure.demo.Repository.IAventureiroRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AventureiroService {

    private final IAventureiroRepository repository;
    private final AventureiroExceptionService exceptionService;


    public AventureiroService(IAventureiroRepository repository, AventureiroExceptionService exceptionService, IMapper mapper) {
        this.repository = repository;
        this.exceptionService = exceptionService;

    }

    @Transactional
    public AventureiroEntity salvar(AventureiroEntity request) {
        // Validações de regra de negócio (TP2)
        if (request.getNivel() < 1) {
            exceptionService.lancar(ErroCatalogo.NIVEL_DE_AVENTUREIRO_MENOR_QUE_UM);
        }
        if (request.getNome() != null && request.getNome().length() > 120) {
            throw new IllegalArgumentException("O nome deve ter no máximo 120 caracteres.");
        }
        if (request.getOrganizacao() == null) {
            throw new IllegalArgumentException("Todo aventureiro deve pertencer a uma organização.");
        }

        request.setAtivo(true); // Aventureiros novos começam ativos por padrão
        return repository.save(request);
    }

    @Transactional(readOnly = true)
    public List<AventureiroEntity> buscarParcial(String nome, Pageable pageable) {
        return repository.buscaParcial(nome, pageable);
    }

    @Transactional(readOnly = true)
    public List<AventureiroEntity> listarPorStatus(boolean ativo, Pageable pageable) {
        return repository.buscaPorStatus(ativo, pageable);
    }

    @Transactional(readOnly = true)
    public List<AventureiroEntity> listarPorClasse(String classe, Pageable pageable) {
        return repository.buscaPorClasse(classe, pageable);
    }

    @Transactional(readOnly = true)
    public List<AventureiroEntity> listarPorNivelMinimo(int nivel, Pageable pageable) {
        return repository.buscarPorNivelMinimo(nivel, pageable);
    }

    @Transactional(readOnly = true)
    public AventureiroResponse obterPerfilCompleto(Long id) {
        // Busca os dados principais, companheiro e total de missões em uma única query (Questão 3 do TP2)
        AventureiroResponse response = repository.buscarAventureiroCompleto(id);

        if (response == null) {
            throw new EntityNotFoundException("Aventureiro não encontrado com o ID: " + id);
        }

        // Busca a última missão separadamente para manter a consistência pedida
        MissaoEntity ultimaMissao = repository.buscarUltimaMissaoDoAventureiro(id);
        response.setUltimaMissao(ultimaMissao);

        return response;
    }

    @Transactional(readOnly = true)
    public List<RankingResponse> gerarRanking() {
        return repository.gerarRankingParticipacao();
    }

    @Transactional
    public void alterarStatus(Long id, boolean ativo) {
        AventureiroEntity aventureiro = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro não encontrado."));
        aventureiro.setAtivo(ativo);
        repository.save(aventureiro);
    }

    @Transactional
    public AventureiroEntity gerenciarCompanheiro(Long id, CompanheiroEntity companheiro) {
        AventureiroEntity aventureiro = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro não encontrado."));

        if (companheiro != null) {
            // Adicionar ou Alterar
            if (companheiro.getNome().length() > 120) {
                throw new IllegalArgumentException("Nome do companheiro deve ter no máximo 120 caracteres.");
            }
            if (companheiro.getLealdade() < 0 || companheiro.getLealdade() > 100) {
                throw new IllegalArgumentException("Lealdade deve estar entre 0 e 100.");
            }
            companheiro.setAventureiro(aventureiro);
            aventureiro.setCompanheiro(companheiro);
        } else {
            // Deletar companheiro
            aventureiro.setCompanheiro(null);
        }

        return repository.save(aventureiro);
    }



    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Aventureiro não encontrado.");
        }
        repository.deleteById(id);
    }
}