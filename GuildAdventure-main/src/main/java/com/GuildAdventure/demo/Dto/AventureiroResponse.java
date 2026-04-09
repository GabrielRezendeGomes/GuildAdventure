package com.GuildAdventure.demo.Dto;

import com.GuildAdventure.demo.Domain.Model.Aventura.entities.CompanheiroEntity;
import com.GuildAdventure.demo.Domain.Model.Aventura.Enums.ClassTypeEnum;

public class AventureiroResponse
{
    private long id;
    private String nome;
    private ClassTypeEnum classe;
    private int nivel;
    private Boolean status;
    private CompanheiroEntity companheiro;

}
