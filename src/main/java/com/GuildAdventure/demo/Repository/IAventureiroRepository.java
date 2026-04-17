package com.GuildAdventure.demo.Repository;

import com.GuildAdventure.demo.Domain.Model.Aventura.Enums.ClassTypeEnum;
import com.GuildAdventure.demo.Domain.Model.Aventura.entities.AventureiroEntity;
import com.GuildAdventure.demo.Domain.Model.Aventura.entities.MissaoEntity;
import com.GuildAdventure.demo.Dto.AventureiroResponse;
import com.GuildAdventure.demo.Dto.RankingResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IAventureiroRepository extends JpaRepository<AventureiroEntity, Long> {

        @Query("SELECT a FROM AventureiroEntity a WHERE LOWER(a.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
        Page<AventureiroEntity> buscaParcial(@Param("nome") String nome, Pageable pageable);

        @Query("SELECT a FROM AventureiroEntity a " +
                "WHERE (:status IS NULL OR a.ativo = :status) " +
                "AND (:classe IS NULL OR a.classe = :classe) " +
                "AND (:nivel IS NULL OR a.nivel >= :nivel)")
        Page<AventureiroEntity> buscarComFiltros(
                @Param("status") Boolean status,
                @Param("classe") ClassTypeEnum classe,
                @Param("nivel") Integer nivel,
                Pageable pageable);

        @Query("SELECT new com.GuildAdventure.demo.Dto.AventureiroResponse(" +
                "a.id, a.nome, a.classe, a.nivel, a.ativo, c, " +
                "(SELECT COUNT(p) FROM ParticipacaoMissao p WHERE p.aventureiroid = a)) " +
                "FROM AventureiroEntity a LEFT JOIN a.companheiro c WHERE a.id = :id")
        AventureiroResponse buscarAventureiroCompleto(@Param("id") Long id);

        @Query("SELECT new com.GuildAdventure.demo.Dto.RankingResponse(a.nome, COUNT(p), COALESCE(SUM(p.recompensa), 0), SUM(CASE WHEN p.destaque = true THEN 1 ELSE 0 END)) " +
                "FROM AventureiroEntity a JOIN a.missoes p " +
                "GROUP BY a.id, a.nome " +
                "ORDER BY COUNT(p) DESC")
        List<RankingResponse> gerarRankingParticipacao();

        @Query(value = "SELECT m.* FROM aventura.missao m " +
                "JOIN aventura.ParticipacaoMissao p ON m.id = p.missao_id " +
                "WHERE p.aventureiro_id = :aventureiroId " +
                "ORDER BY p.data_registro DESC LIMIT 1", nativeQuery = true)
        MissaoEntity buscarUltimaMissaoDoAventureiro(@Param("aventureiroId") Long aventureiroId);
}