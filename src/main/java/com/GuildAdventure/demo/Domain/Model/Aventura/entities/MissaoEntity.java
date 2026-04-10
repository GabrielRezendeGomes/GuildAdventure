package com.GuildAdventure.demo.Domain.Model.Aventura.entities;

import com.GuildAdventure.demo.Domain.Model.Audit.entities.OrganizacoesEntity;
import com.GuildAdventure.demo.Domain.Model.Aventura.Enums.NivelDePerigoTypeEnum;
import com.GuildAdventure.demo.Domain.Model.Aventura.Enums.StatusMissaoEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "missao", schema = "aventura")
@Getter
@Setter
public class MissaoEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "titulo", nullable = false, length = 150)
    private String titulo;

    @ManyToOne
    @JoinColumn(name = "organizacao_id", nullable = false)
    private OrganizacoesEntity organizacao;

    @Column(name = "nivel_perigo", nullable = false)
    private NivelDePerigoTypeEnum nivelPerigo;

    @Column(name = "status", nullable = false)
    private StatusMissaoEnum statusMissao;

    @Column(name = "data_criacao")
    private OffsetDateTime dataCriacao;

    @Column(name = "data_inicio")
    private OffsetDateTime dataInicio;

    @Column(name = "data_fim")
    private OffsetDateTime dataFim;

    @OneToMany(mappedBy = "missaoid")
    private Set<Participacao_missao> aventureirosInscritos = new HashSet<>();


    @PrePersist
    public void OnCreate()
    {
        this.dataCriacao = OffsetDateTime.now();
    }


}
