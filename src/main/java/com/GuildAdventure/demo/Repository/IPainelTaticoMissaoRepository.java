package com.GuildAdventure.demo.Repository;

import com.GuildAdventure.demo.Domain.Model.Operacoes.entities.PainelTaticoMissaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;

public interface IPainelTaticoMissaoRepository extends JpaRepository<PainelTaticoMissaoEntity, Long> {

    @Query("SELECT p FROM PainelTaticoMissaoEntity p WHERE p.ultimaAtualizacao > :data ORDER BY p.indiceProntidao DESC LIMIT 10")
    List<PainelTaticoMissaoEntity> buscarTop10Recentes(@Param("data") OffsetDateTime data);

}