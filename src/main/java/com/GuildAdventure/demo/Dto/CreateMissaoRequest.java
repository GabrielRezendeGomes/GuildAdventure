package com.GuildAdventure.demo.Dto;

import com.GuildAdventure.demo.Domain.Model.Aventura.Enums.NivelDePerigoTypeEnum;
import jakarta.validation.constraints.*;

import java.time.OffsetDateTime;

public record CreateMissaoRequest(

        @NotBlank(message = "O título da missão é obrigatório.")
        @Size(max = 150, message = "O título deve ter no máximo 150 caracteres.")
        String titulo,

        @NotNull(message = "O ID da organização responsável é obrigatório.")
        Long organizacaoId,

        @NotNull(message = "O nível de perigo é obrigatório.")
        NivelDePerigoTypeEnum nivelPerigo,

        @FutureOrPresent(message = "A data de início deve ser no presente ou no futuro.")
                OffsetDateTime dataInicio,


        @Future(message = "A data de fim deve estar no futuro.")
        OffsetDateTime dataFim


) {}