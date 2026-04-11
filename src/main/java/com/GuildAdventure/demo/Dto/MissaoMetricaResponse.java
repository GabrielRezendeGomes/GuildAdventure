package com.GuildAdventure.demo.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MissaoMetricaResponse {
    private String titulo;
    private String status;
    private String nivelPerigo;
    private Long quantidadeParticipantes;
    private Double totalRecompensas;
}