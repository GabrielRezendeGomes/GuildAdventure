package com.GuildAdventure.demo.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;


@Getter
@Setter
@AllArgsConstructor
public class AventureiroResponseSimples
{
        private String nome;
        private Long id;
        private String classe;
        private Integer nivel;
        private Boolean status;
}




