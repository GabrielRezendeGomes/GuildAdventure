package com.GuildAdventure.demo.Repository;

import com.GuildAdventure.demo.Domain.Model.Audit.entities.OrganizacoesEntity;
import com.GuildAdventure.demo.Domain.Model.Aventura.entities.AventureiroEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrganizacaoRepository  extends JpaRepository<OrganizacoesEntity, Long>
{


}
