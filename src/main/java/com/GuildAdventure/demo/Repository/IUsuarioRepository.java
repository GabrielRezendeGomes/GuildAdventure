package com.GuildAdventure.demo.Repository;

import com.GuildAdventure.demo.Domain.Model.Audit.entities.OrganizacoesEntity;
import com.GuildAdventure.demo.Domain.Model.Audit.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
}
