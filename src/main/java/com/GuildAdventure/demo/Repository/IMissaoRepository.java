package com.GuildAdventure.demo.Repository;

import com.GuildAdventure.demo.Domain.Model.Aventura.entities.MissaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMissaoRepository extends JpaRepository<MissaoEntity, Long>
{



}
