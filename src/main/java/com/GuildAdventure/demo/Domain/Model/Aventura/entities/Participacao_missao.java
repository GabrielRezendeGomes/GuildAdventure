package com.GuildAdventure.demo.Domain.Model.Aventura.entities;

import com.GuildAdventure.demo.Domain.Model.Audit.entities.user_role_id;
import com.GuildAdventure.demo.Domain.Model.Aventura.Enums.PapeisMissaoEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "participacao_missao",
        schema = "aventura",
        uniqueConstraints = @UniqueConstraint(columnNames = {"missao_id", "aventureiro_id"}))
@Getter
@Setter
public class Participacao_missao
{
    @EmbeddedId
    private participacao_missao_id id;

    @ManyToOne
    @MapsId("aventureiroId")
    @JoinColumn(name = "aventureiro_id", nullable = false)
    private AventureiroEntity aventureiroid;

    @ManyToOne
    @MapsId("missaoId")
    @JoinColumn(name = "missao_id", nullable = false)
    @JsonIgnore
    private MissaoEntity missaoid;

    @Column(name = "papel", nullable = false)
    private PapeisMissaoEnum papel;

    @Column(name = "recompensa_ouro", precision = 10, scale = 2)
    @org.hibernate.annotations.Check(constraints = "recompensa_ouro >= 0")
    private java.math.BigDecimal recompensa;

    @Column(name = "destaque")
    private boolean destaque = false;

    @Column(name = "data_registro")
    private OffsetDateTime data_registro;


    @PrePersist
    public void OnCreate()
    {
        this.data_registro = OffsetDateTime.now();
    }

}
