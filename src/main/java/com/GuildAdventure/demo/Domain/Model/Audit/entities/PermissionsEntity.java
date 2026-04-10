package com.GuildAdventure.demo.Domain.Model.Audit.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "permissions",
        schema = "audit",
        uniqueConstraints = @UniqueConstraint(columnNames = {"code"}))
@Getter
@Setter
public class PermissionsEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @Column(name = "code", nullable = false, length = 80)
    private String code;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @ManyToMany(mappedBy = "permissions")
    private Set<RolesEntity> roles = new HashSet<>();



}
