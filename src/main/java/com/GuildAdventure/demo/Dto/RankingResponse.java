package com.GuildAdventure.demo.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class RankingResponse {
    private String nome;
    private Long totalParticipacoes;
    private BigDecimal somaRecompensas;
    private Long quantidadeDestaques;
}