package com.GuildAdventure.demo.Dto;

import com.GuildAdventure.demo.Domain.Model.Aventura.Enums.ClassTypeEnum;
import lombok.Data;

@Data
public class AventureiroRequest
{
    private String nome;
    private ClassTypeEnum classe;
    private int nivel;
}
