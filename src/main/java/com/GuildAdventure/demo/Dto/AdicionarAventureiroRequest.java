package com.GuildAdventure.demo.Dto;

import com.GuildAdventure.demo.Domain.Model.Aventura.Enums.PapeisMissaoEnum;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AdicionarAventureiroRequest(
        @NotNull(message = "O ID do aventureiro é obrigatório.")
        Long aventureiroId,

        @NotNull(message = "O papel na missão é obrigatório.")
        PapeisMissaoEnum papel,

        BigDecimal recompensaOuroInicial) { }
