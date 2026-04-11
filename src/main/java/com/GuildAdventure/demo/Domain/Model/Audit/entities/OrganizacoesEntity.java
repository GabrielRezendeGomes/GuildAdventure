package com.GuildAdventure.demo.Domain.Model.Audit.entities;

import com.GuildAdventure.demo.Domain.Model.Aventura.entities.AventureiroEntity;
import com.GuildAdventure.demo.Domain.Model.Aventura.entities.MissaoEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "organizacoes", schema = "audit")
@Getter
@Setter
public class OrganizacoesEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name = "nome", nullable = false, length = 120)
    private String nome;
    @Column(name = "ativo", nullable = false)
    private boolean ativo = true;
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @OneToMany(mappedBy = "organizacao")
    @JsonIgnore
    private List<AventureiroEntity> aventureiros;

    @OneToMany(mappedBy = "organizacao")
    private Set<UsuarioEntity> usuarios = new HashSet<>();

    @OneToMany(mappedBy = "organizacao")
    private Set<Api_keysEntity> apiKeys = new HashSet<>();

    @OneToMany(mappedBy = "organizacao")
    private Set<MissaoEntity> missoes = new HashSet<>();

    @PrePersist
    public void OnCreate()
    {
        this.createdAt = OffsetDateTime.now();
    }



}
