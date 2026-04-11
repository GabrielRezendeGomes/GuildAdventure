package com.GuildAdventure.demo.Domain.Model.Aventura.entities;

import com.GuildAdventure.demo.Domain.Model.Audit.entities.OrganizacoesEntity;
import com.GuildAdventure.demo.Domain.Model.Audit.entities.UsuarioEntity;
import com.GuildAdventure.demo.Domain.Model.Aventura.Enums.ClassTypeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "aventureiro", schema = "aventura")
public class AventureiroEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "nome", nullable = false, length = 120)
    private String nome;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ClassTypeEnum classe;

    @Column(name = "nivel", nullable = false)
    @Min(1)
    private int nivel;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    @ManyToOne
    @JoinColumn(name = "organizacao_id", nullable = false)
    private OrganizacoesEntity organizacao;

    @ManyToOne
    @JoinColumn(name = "usuario_cadastro_id", nullable = false)
    private UsuarioEntity usuarioCadastro;

    @OneToOne(mappedBy = "aventureiro", cascade = CascadeType.ALL)
    private CompanheiroEntity companheiro;

    @Column(name = "data_criacao")
    private OffsetDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private OffsetDateTime dataAtualizacao;

    @OneToMany(mappedBy = "aventureiroid")
    private Set<Participacao_missao> missoes = new HashSet<>();

    @PrePersist
    public void OnCreate() {
        this.dataCriacao = OffsetDateTime.now();
    }

    @PreUpdate
    public void OnUpdate() {
        this.dataAtualizacao = OffsetDateTime.now();
    }
}
