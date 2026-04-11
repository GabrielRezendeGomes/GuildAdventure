package com.GuildAdventure.demo.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAventureiroRequest {
    private String nome;
    private Long organizacao_id;
    private String classe;
    private int nivel;
}