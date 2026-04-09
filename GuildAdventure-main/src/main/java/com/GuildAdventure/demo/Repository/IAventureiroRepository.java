package com.GuildAdventure.demo.Repository;

import com.GuildAdventure.demo.Domain.Model.Aventura.entities.AventureiroEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAventureiroRepository extends JpaRepository<AventureiroEntity, Long> {
}
