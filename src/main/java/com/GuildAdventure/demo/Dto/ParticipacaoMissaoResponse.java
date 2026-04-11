package com.GuildAdventure.demo.Dto;

import com.GuildAdventure.demo.Domain.Model.Aventura.Enums.PapeisMissaoEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ParticipacaoMissaoResponse {
    private Long aventureiroId;
    private String nomeAventureiro;
    private PapeisMissaoEnum papel;
    private BigDecimal recompensa;
    private boolean destaque;
}