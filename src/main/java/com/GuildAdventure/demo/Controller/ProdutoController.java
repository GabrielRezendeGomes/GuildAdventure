package com.GuildAdventure.demo.Controller;

import com.GuildAdventure.demo.Domain.Model.Loja.entities.ProdutoDocument;
import com.GuildAdventure.demo.Service.ProdutoElasticService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoElasticService service;

    public ProdutoController(ProdutoElasticService service) {
        this.service = service;
    }

    // Parte A
    @GetMapping("/busca/nome")
    public List<ProdutoDocument> buscarPorNome(@RequestParam String termo) { return service.buscarPorNome(termo); }

    @GetMapping("/busca/descricao")
    public List<ProdutoDocument> buscarPorDescricao(@RequestParam String termo) { return service.buscarPorDescricao(termo); }

    @GetMapping("/busca/frase")
    public List<ProdutoDocument> buscarPorFrase(@RequestParam String termo) { return service.buscarPorFraseExata(termo); }

    @GetMapping("/busca/fuzzy")
    public List<ProdutoDocument> buscarFuzzy(@RequestParam String termo) { return service.buscarFuzzy(termo); }

    @GetMapping("/busca/multicampos")
    public List<ProdutoDocument> buscarMultiCampos(@RequestParam String termo) { return service.buscarMultiCampos(termo); }

    // Parte B
    @GetMapping("/busca/com-filtro")
    public List<ProdutoDocument> buscarComFiltro(@RequestParam String termo, @RequestParam String categoria) {
        return service.buscarComFiltro(termo, categoria);
    }

    @GetMapping("/busca/faixa-preco")
    public List<ProdutoDocument> buscarFaixaPreco(@RequestParam Double min, @RequestParam Double max) {
        return service.buscarFaixaPreco(min, max);
    }

    @GetMapping("/busca/avancada")
    public List<ProdutoDocument> buscaAvancada(@RequestParam String categoria, @RequestParam String raridade, @RequestParam Double min, @RequestParam Double max) {
        return service.buscaAvancada(categoria, raridade, min, max);
    }

    // Parte C
    @GetMapping("/agregacoes/por-categoria")
    public Map<String, Long> agregacaoPorCategoria() throws IOException { return service.agregacaoPorCategoria(); }

    @GetMapping("/agregacoes/por-raridade")
    public Map<String, Long> agregacaoPorRaridade() throws IOException { return service.agregacaoPorRaridade(); }

    @GetMapping("/agregacoes/preco-medio")
    public Double agregacaoPrecoMedio() throws IOException { return service.agregacaoPrecoMedio(); }

    @GetMapping("/agregacoes/faixas-preco")
    public Map<String, Long> agregacaoFaixasPreco() throws IOException { return service.agregacaoFaixasPreco(); }
}