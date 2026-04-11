package com.GuildAdventure.demo.Domain.Model.Audit.entities;

import com.GuildAdventure.demo.Domain.Model.Aventura.entities.AventureiroEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "usuarios",
        schema = "audit",
        uniqueConstraints = @UniqueConstraint(columnNames = {"organizacao_id", "email"}))
@Getter
@Setter
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "nome", nullable = false, length = 120)
    private String nome;

    @Column(name = "email", nullable = false, length = 180)
    private String email;

    @Column(name = "senha_hash", nullable = false)
    private String senha_hash;

    @Column(name = "ultimo_login_em")
    private OffsetDateTime ultimo_login_em;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime created_at;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updated_at;

    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacao_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private OrganizacoesEntity organizacao;

    @OneToMany(mappedBy = "usuario")
    private List<User_RolesEntity> userRoles;

    @OneToMany(mappedBy = "usuarioCadastro")
    private List<AventureiroEntity> aventureirosCadastrados;

    @PrePersist
    public void OnCreate() {
        this.created_at = OffsetDateTime.now();
        this.updated_at = OffsetDateTime.now();
    }

    @PreUpdate
    public void OnUpdate() {
        this.updated_at = OffsetDateTime.now();
    }
}
