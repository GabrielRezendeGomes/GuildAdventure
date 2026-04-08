package com.GuildAdventure.demo.Exception;
import lombok.Getter;


import java.util.List;

@Getter
public class AventureiroException extends RuntimeException
{

    private final ErroCatalogo _tipo;
    private final List<String> _detalhes;

    public AventureiroException(ErroCatalogo _tipo, List<String> _detalhes)
    {
        super(_tipo.getMensagem());
        this._tipo = _tipo;
        this._detalhes = _detalhes;
    }
}
