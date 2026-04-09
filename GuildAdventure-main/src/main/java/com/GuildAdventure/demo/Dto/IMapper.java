package com.GuildAdventure.demo.Dto;

import com.GuildAdventure.demo.Domain.Model.Aventura.entities.AventureiroEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IMapper
{
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "companheiro", ignore = true)
    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "classe", source = "classe")
    @Mapping(target = "nivel", source = "nivel")
    AventureiroEntity toEntity(AventureiroRequest request);


    AventureiroResponse toResponse(AventureiroEntity aventureiro);


    AventureiroResponseSimples toSimpleResponse(AventureiroEntity aventureiro);
}
