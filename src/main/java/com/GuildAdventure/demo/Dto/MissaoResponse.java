package com.GuildAdventure.demo.Dto;

import com.GuildAdventure.demo.Domain.Model.Aventura.Enums.NivelDePerigoTypeEnum;
import com.GuildAdventure.demo.Domain.Model.Aventura.Enums.StatusMissaoEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class MissaoResponse {
    private long id;
    private String titulo;
    private NivelDePerigoTypeEnum nivelPerigo;
    private StatusMissaoEnum statusMissao;
    private OffsetDateTime dataInicio;
    private OffsetDateTime dataFim;
    private List<ParticipacaoMissaoResponse> participantes;
}