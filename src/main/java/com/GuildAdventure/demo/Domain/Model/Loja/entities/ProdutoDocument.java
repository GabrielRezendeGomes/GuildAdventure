package com.GuildAdventure.demo.Domain.Model.Loja.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "guilda_loja")
@Getter
@Setter
public class ProdutoDocument {
    @Id
    private String id;
    private String nome;
    private String descricao;
    private String categoria;
    private String raridade;
    private Double preco;
}