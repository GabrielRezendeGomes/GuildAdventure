package com.GuildAdventure.demo.Exception;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AventureiroExceptionService
{
    public void lancar(ErroCatalogo tipo, List<String> detalhes)
    {
        throw new AventureiroException(tipo, detalhes);
    }
    public void lancar(ErroCatalogo tipo)
    {
        throw new AventureiroException(tipo, Collections.emptyList());
    }

}
