package com.GuildAdventure.demo.Service;

import com.GuildAdventure.demo.Domain.Model.Aventura.Enums.NivelDePerigoTypeEnum;
import com.GuildAdventure.demo.Domain.Model.Aventura.Enums.StatusMissaoEnum;
import com.GuildAdventure.demo.Domain.Model.Aventura.entities.MissaoEntity;
import com.GuildAdventure.demo.Dto.IMapper;
import com.GuildAdventure.demo.Dto.MissaoMetricasResponse;
import com.GuildAdventure.demo.Dto.MissaoResponse;
import com.GuildAdventure.demo.Repository.IMissaoRepository;
import jakarta.persistence.EntityNotFoundException;
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

    public MissaoService(IMissaoRepository repository, IMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public Page<MissaoEntity> buscarComFiltros(StatusMissaoEnum status,
                                               NivelDePerigoTypeEnum nivelPerigo,
                                               OffsetDateTime dataInicio,
                                               OffsetDateTime dataFim,
                                               Pageable pageable) {
        return repository.buscarComFiltros(status, nivelPerigo, dataInicio, dataFim, pageable);
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
}