package com.GuildAdventure.demo.Domain.Model.Audit.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "api_keys",
        schema = "audit",
        uniqueConstraints = @UniqueConstraint(columnNames = {"organizacao_id", "nome"}))
@Getter
@Setter

public class ApiKeysEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacao_id", nullable = false)
    private OrganizacoesEntity organizacao;

    @Column(name = "nome" ,nullable = false, length = 120)
    private String nome;

    @Column(name = "key_hash", nullable = false)
    private String key_hash;

    @Column(name = "ativo" ,nullable = false)
    private boolean ativo = true;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime created_at;

    @Column(name = "last_used_at")
    private OffsetDateTime last_used_at;

    @PrePersist
    public void OnCreate()
    {
        this.created_at = OffsetDateTime.now();
    }



}
