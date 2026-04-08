package com.GuildAdventure.demo.Domain.entities;

import com.GuildAdventure.demo.Domain.enums.ClassTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Aventureiros")
public class AventureiroEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String nome;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ClassTypeEnum classe;
    private int nivel;
    private Boolean status;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "companheiroID", referencedColumnName = "id")
    private CompanheiroEntity companheiro;



}
