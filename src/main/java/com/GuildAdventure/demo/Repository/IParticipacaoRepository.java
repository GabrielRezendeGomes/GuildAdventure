package com.GuildAdventure.demo.Repository;

import com.GuildAdventure.demo.Domain.Model.Aventura.entities.ParticipacaoMissao;
import com.GuildAdventure.demo.Domain.Model.Aventura.entities.ParticipacaoMissaoId;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IParticipacaoRepository extends JpaRepository<ParticipacaoMissao, ParticipacaoMissaoId> {

}