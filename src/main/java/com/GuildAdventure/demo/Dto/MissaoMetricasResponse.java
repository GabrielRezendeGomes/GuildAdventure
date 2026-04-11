package com.GuildAdventure.demo.Dto;

import com.GuildAdventure.demo.Domain.Model.Aventura.Enums.StatusMissaoEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import com.GuildAdventure.demo.Domain.Model.Aventura.Enums.NivelDePerigoTypeEnum;
import com.GuildAdventure.demo.Domain.Model.Aventura.Enums.StatusMissaoEnum;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class MissaoMetricasResponse {
    private String titulo;
    private StatusMissaoEnum status;
    private NivelDePerigoTypeEnum nivelPerigo;
    private Long quantidadeParticipantes;
    private BigDecimal totalRecompensas;
}
