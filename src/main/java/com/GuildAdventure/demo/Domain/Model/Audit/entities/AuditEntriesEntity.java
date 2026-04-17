package com.GuildAdventure.demo.Domain.Model.Audit.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import com.fasterxml.jackson.databind.JsonNode;

import java.time.OffsetDateTime;

@Entity
@Table(name = "audit_entries", schema = "audit")
@Getter
@Setter
public class AuditEntriesEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacao_id", nullable = false)
    private OrganizacoesEntity organizacaoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_agent_id")
    private UsuarioEntity actorUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_api_key_id")
    private ApiKeysEntity actorApiKeyId;

    @Column(nullable = false)
    private String action;

    @Column(name = "occurred_at", nullable = false)
    private OffsetDateTime occurredAt;

    @Column(columnDefinition = "inet")
    private String ip;

    @Type(io.hypersistence.utils.hibernate.type.json.JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private JsonNode diff;

    @Type(io.hypersistence.utils.hibernate.type.json.JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private JsonNode metadata;

    @Column(nullable = false)
    private Boolean success;


    @PrePersist
    public void OnCreate()
    {
        this.occurredAt = OffsetDateTime.now();
    }
}
