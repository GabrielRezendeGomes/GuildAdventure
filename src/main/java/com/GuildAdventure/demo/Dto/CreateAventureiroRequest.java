package com.GuildAdventure.demo.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Getter
@Setter
@AllArgsConstructor
public class CreateAventureiroRequest
{
    private String nome;
    private Long organizacao_id;
    private String classe;
    private Long usuario_cadastrado_id;
    private int nivel;
}
