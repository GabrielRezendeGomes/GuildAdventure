package com.GuildAdventure.demo.Exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public interface IException
{
    String getMensagem();
    List<String> getDetalhes();
    HttpStatus getStatus();
}
