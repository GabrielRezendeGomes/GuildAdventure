package com.GuildAdventure.demo.Dto;

import com.GuildAdventure.demo.Domain.Model.Aventura.Enums.ClassTypeEnum;
import com.GuildAdventure.demo.Domain.Model.Aventura.entities.CompanheiroEntity;
import com.GuildAdventure.demo.Domain.Model.Aventura.entities.MissaoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AventureiroResponse {
    private Long id;
    private String nome;
    private ClassTypeEnum classe;
    private int nivel;
    private Boolean status;
    private CompanheiroEntity companheiro;
    private Long quantidadeTotalDeMissoes;
    private MissaoEntity ultimaMissao;

    public AventureiroResponse(Long id, String nome, ClassTypeEnum classe, int nivel,
                               Boolean status, CompanheiroEntity companheiro, Long quantidadeTotalDeMissoes) {
        this.id = id;
        this.nome = nome;
        this.classe = classe;
        this.nivel = nivel;
        this.status = status;
        this.companheiro = companheiro;
        this.quantidadeTotalDeMissoes = quantidadeTotalDeMissoes;
        this.ultimaMissao = null;
    }
}
