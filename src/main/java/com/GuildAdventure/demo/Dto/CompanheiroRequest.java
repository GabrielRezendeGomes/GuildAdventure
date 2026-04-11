package com.GuildAdventure.demo.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanheiroRequest {
    private String nome;
    private String tipoDeCompanheiro;
    private int lealdade;
}