package com.GuildAdventure.demo.Repository;

import com.GuildAdventure.demo.Domain.Model.Aventura.entities.MissaoEntity;
import com.GuildAdventure.demo.Domain.Model.Aventura.Enums.NivelDePerigoTypeEnum;
import com.GuildAdventure.demo.Domain.Model.Aventura.Enums.StatusMissaoEnum;
import com.GuildAdventure.demo.Dto.MissaoMetricasResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface IMissaoRepository extends JpaRepository<MissaoEntity, Long> {

    // Relatório de Métricas
    @Query("SELECT new com.GuildAdventure.demo.Dto.MissaoMetricasResponse(" +
            "m.titulo, m.statusMissao, m.nivelPerigo, COUNT(p), COALESCE(SUM(p.recompensa), 0)) " +
            "FROM MissaoEntity m LEFT JOIN m.aventureirosInscritos p " +
            "GROUP BY m.id, m.titulo, m.statusMissao, m.nivelPerigo")
    List<MissaoMetricasResponse> gerarRelatorioMetricas();

    // Listagem de Missões com Filtros
    @Query("SELECT m FROM MissaoEntity m " +
            "WHERE (:status IS NULL OR m.statusMissao = :status) " +
            "AND (:nivelPerigo IS NULL OR m.nivelPerigo = :nivelPerigo) " +
            "AND (cast(:dataInicio as timestamp) IS NULL OR m.dataCriacao >= :dataInicio) " +
            "AND (cast(:dataFim as timestamp) IS NULL OR m.dataCriacao <= :dataFim)")
    Page<MissaoEntity> buscarComFiltros(
            @Param("status") StatusMissaoEnum status,
            @Param("nivelPerigo") NivelDePerigoTypeEnum nivelPerigo,
            @Param("dataInicio") OffsetDateTime dataInicio,
            @Param("dataFim") OffsetDateTime dataFim,
            Pageable pageable);

    // Detalhamento de Missão (Trazendo os aventureiros inscritos para evitar N+1)
    @Query("SELECT m FROM MissaoEntity m " +
            "LEFT JOIN FETCH m.aventureirosInscritos p " +
            "LEFT JOIN FETCH p.aventureiroid " +
            "WHERE m.id = :id")
    Optional<MissaoEntity> buscarDetalhesMissao(@Param("id") Long id);
}