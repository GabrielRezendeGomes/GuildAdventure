package com.GuildAdventure.demo.Exception;
import lombok.Getter;


import java.util.List;

public class AventureiroException extends RuntimeException {
    private final ErroCatalogo tipo;
    private final List<String> detalhes;

    public AventureiroException(ErroCatalogo tipo, List<String> detalhes) {
        super(tipo.getMensagem());
        this.tipo = tipo;
        this.detalhes = detalhes;
    }

    public ErroCatalogo getTipo() { return tipo; }
    public List<String> getDetalhes() { return detalhes; }
}