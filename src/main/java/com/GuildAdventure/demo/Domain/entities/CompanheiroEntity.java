package com.GuildAdventure.demo.Domain.entities;

import com.GuildAdventure.demo.Domain.enums.CompanionTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Companheiros")
public class CompanheiroEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String nome;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CompanionTypeEnum tipoDeCompanheiro;
    private int lealdade;



}
