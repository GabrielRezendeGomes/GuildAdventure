package com.GuildAdventure.demo.Domain.Model.Aventura.entities;

import com.GuildAdventure.demo.Domain.Model.Aventura.Enums.CompanionTypeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "companheiro", schema = "aventura")
public class CompanheiroEntity
{
    @Id
    private long id;

    @Column(name = "nome", nullable = false, length = 120)
    private String nome;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CompanionTypeEnum tipoDeCompanheiro;

    @Min(0)
    @Max(100)
    @Column(name = "indice_lealdade", nullable = false)
    private int lealdade;

    @OneToOne
    @MapsId
    @JoinColumn(name = "aventureiro_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AventureiroEntity aventureiro;



}
