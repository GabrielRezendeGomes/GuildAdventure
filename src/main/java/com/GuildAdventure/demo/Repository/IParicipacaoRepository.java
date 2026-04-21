package com.GuildAdventure.demo.Repository;

import com.GuildAdventure.demo.Domain.Model.Aventura.entities.ParticipacaoMissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IParicipacaoRepository extends JpaRepository<ParticipacaoMissao, Long>
{
    @Query("SELECT p.missaoid FROM ParticipacaoMissao p WHERE p.aventureiroid = :aventureiroId")
    List<Long> findMissoesByAventureiro(@Param("aventureiroId") Long aventureiroId);
}
