package com.GuildAdventure.demo.Dto;

import com.GuildAdventure.demo.Domain.entities.AventureiroEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IMapper
{
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "companheiro", ignore = true)
    AventureiroEntity toEntity(AventureiroRequest request);


    AventureiroResponse toResponse(AventureiroEntity aventureiro);

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "companheiro", ignore = true)
    AventureiroResponseSimples toSimpleResponse(AventureiroEntity aventureiro);
}
