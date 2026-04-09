package com.GuildAdventure.demo.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Builder
@Getter
@Setter
@AllArgsConstructor
public class ErroResponse
{
    private String mensagem;
    private List<String> detalhes;
    private long timestamp;
}
