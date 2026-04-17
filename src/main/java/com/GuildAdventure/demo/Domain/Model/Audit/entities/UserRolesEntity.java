package com.GuildAdventure.demo.Domain.Model.Audit.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "user_roles", schema = "audit")
@Getter
@Setter
public class UserRolesEntity
{
    @EmbeddedId
    private UserRoleId id;

    @ManyToOne
    @MapsId("usuarioId")
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEntity usuario;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id" , nullable = false)
    private  RolesEntity role;

    @Column(name = "granted_at", nullable = false)
    private OffsetDateTime granted_at;


    @PrePersist
    public void OnCreate()
    {
        this.granted_at = OffsetDateTime.now();
    }
}
