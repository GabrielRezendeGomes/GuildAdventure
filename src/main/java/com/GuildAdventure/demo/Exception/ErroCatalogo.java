package com.GuildAdventure.demo.Exception;

import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


public enum ErroCatalogo
{


    AVENTUREIRO_INVALIDO("Aventureiro invalido", HttpStatus.BAD_REQUEST),
    AVENTUREIRO_NAO_ENCONTRADO("Aventureiro não encontrado", HttpStatus.NOT_FOUND),
    NIVEL_DE_AVENTUREIRO_MENOR_QUE_UM("O nivel tem que ser maior que 1", HttpStatus.BAD_REQUEST),
    AVENTUREIRO_DEVE_TER_UMA_ORGANIZACAO("O aventureiro deve ter uma organizacao", HttpStatus.BAD_REQUEST),
    ORGANIZACAO_NAO_EXISTE("Organizacao nao existe", HttpStatus.BAD_REQUEST),
    USUARIO_INVALIDO("O usuario selecionado nao e valido", HttpStatus.BAD_REQUEST),
    AVENTUREIRO_DEVE_TER_UM_USUARIO("O aventureiro deve ter um usuario", HttpStatus.BAD_REQUEST);

    private final String mensagem;
    private final HttpStatus status;

    ErroCatalogo(String mensagem, HttpStatus status) {
        this.mensagem = mensagem;
        this.status = status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public HttpStatus getStatus() {
        return status;
    }


}
