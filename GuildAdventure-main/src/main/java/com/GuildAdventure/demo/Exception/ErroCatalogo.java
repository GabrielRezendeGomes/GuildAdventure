package com.GuildAdventure.demo.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErroCatalogo
{
    AVENTUREIRO_INVALIDO("Aventureiro invalido", HttpStatus.BAD_REQUEST),
    AVENTUREIRO_NAO_ENCONTRADO("Aventureiro não encontrado", HttpStatus.NOT_FOUND);


    private final String mensagem;
    private final HttpStatus status;

}
