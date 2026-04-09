package com.GuildAdventure.demo.Domain.Model.Audit.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "roles",
        schema = "audit",
        uniqueConstraints = @UniqueConstraint(columnNames = {"organizacao_id", "nome"}))
@Getter
@Setter
public class RolesEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacao_id", nullable = false)
    private OrganizacoesEntity organizacao;

    @Column(name = "nome", nullable = false, length = 60)
    private String nome;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @OneToMany(mappedBy = "roles")
    private List<User_RolesEntity> usuariosNoPapel;


    @ManyToMany
    @JoinTable(name = "permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permissions_id"))
    private Set<PermissionsEntity> permissions = new HashSet<>();

    @PrePersist
    public void OnCreate()
    {
        this.createdAt = OffsetDateTime.now();
    }

}
