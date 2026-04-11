package com.GuildAdventure.demo.Service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.GuildAdventure.demo.Domain.Model.Loja.entities.ProdutoDocument;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProdutoElasticService {

    private final ElasticsearchOperations elasticsearchOperations;
    private final ElasticsearchClient esClient;

    public ProdutoElasticService(ElasticsearchOperations elasticsearchOperations, ElasticsearchClient esClient) {
        this.elasticsearchOperations = elasticsearchOperations;
        this.esClient = esClient;
    }

    // --- PARTE A: BUSCAS TEXTUAIS ---
    public List<ProdutoDocument> buscarPorNome(String termo) {
        Criteria criteria = new Criteria("nome").matches(termo);
        return executarQuery(new CriteriaQuery(criteria));
    }

    public List<ProdutoDocument> buscarPorDescricao(String termo) {
        Criteria criteria = new Criteria("descricao").matches(termo);
        return executarQuery(new CriteriaQuery(criteria));
    }

    public List<ProdutoDocument> buscarPorFraseExata(String termo) {
        String jsonQuery = "{ \"match_phrase\": { \"descricao\": \"" + termo + "\" } }";
        return executarQuery(new StringQuery(jsonQuery));
    }

    public List<ProdutoDocument> buscarFuzzy(String termo) {
        String jsonQuery = "{ \"match\": { \"nome\": { \"query\": \"" + termo + "\", \"fuzziness\": \"AUTO\" } } }";
        return executarQuery(new StringQuery(jsonQuery));
    }

    public List<ProdutoDocument> buscarMultiCampos(String termo) {
        String jsonQuery = "{ \"multi_match\": { \"query\": \"" + termo + "\", \"fields\": [\"nome\", \"descricao\"] } }";
        return executarQuery(new StringQuery(jsonQuery));
    }

    // --- PARTE B: BUSCAS COM FILTROS ---
    public List<ProdutoDocument> buscarComFiltro(String termo, String categoria) {
        Criteria criteria = new Criteria("descricao").matches(termo).and("categoria").is(categoria);
        return executarQuery(new CriteriaQuery(criteria));
    }

    public List<ProdutoDocument> buscarFaixaPreco(Double min, Double max) {
        Criteria criteria = new Criteria("preco").between(min, max);
        return executarQuery(new CriteriaQuery(criteria));
    }

    public List<ProdutoDocument> buscaAvancada(String categoria, String raridade, Double min, Double max) {
        Criteria criteria = new Criteria("categoria").is(categoria)
                .and("raridade").is(raridade)
                .and("preco").between(min, max);
        return executarQuery(new CriteriaQuery(criteria));
    }

    private List<ProdutoDocument> executarQuery(org.springframework.data.elasticsearch.core.query.Query query) {
        return elasticsearchOperations.search(query, ProdutoDocument.class)
                .getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList());
    }

    // --- PARTE C: AGREGAÇÕES ---
    public Map<String, Long> agregacaoPorCategoria() throws IOException {
        SearchResponse<Void> response = esClient.search(s -> s.index("guilda_loja").size(0)
                // AQUI: Tiramos o .keyword e deixamos apenas "categoria"
                .aggregations("por_categoria", a -> a.terms(t -> t.field("categoria"))), Void.class);
        Map<String, Long> result = new HashMap<>();
        response.aggregations().get("por_categoria").sterms().buckets().array().forEach(b -> result.put(b.key().stringValue(), b.docCount()));
        return result;
    }

    public Map<String, Long> agregacaoPorRaridade() throws IOException {
        SearchResponse<Void> response = esClient.search(s -> s.index("guilda_loja").size(0)
                // AQUI: Tiramos o .keyword e deixamos apenas "raridade"
                .aggregations("por_raridade", a -> a.terms(t -> t.field("raridade"))), Void.class);
        Map<String, Long> result = new HashMap<>();
        response.aggregations().get("por_raridade").sterms().buckets().array().forEach(b -> result.put(b.key().stringValue(), b.docCount()));
        return result;
    }

    public Double agregacaoPrecoMedio() throws IOException {
        SearchResponse<Void> response = esClient.search(s -> s.index("guilda_loja").size(0)
                .aggregations("preco_medio", a -> a.avg(avg -> avg.field("preco"))), Void.class);
        return response.aggregations().get("preco_medio").avg().value();
    }

    public Map<String, Long> agregacaoFaixasPreco() throws IOException {
        SearchResponse<Void> response = esClient.search(s -> s.index("guilda_loja").size(0)
                .aggregations("faixas", a -> a.range(r -> r.field("preco")
                        .ranges(rng -> rng.to("100.0"))
                        .ranges(rng -> rng.from("100.0").to("300.0"))
                        .ranges(rng -> rng.from("300.0").to("700.0"))
                        .ranges(rng -> rng.from("700.0"))
                )), Void.class);

        Map<String, Long> result = new HashMap<>();
        response.aggregations().get("faixas").range().buckets().array().forEach(b -> {
            String key = (b.from() != null ? String.valueOf(b.from()) : "0") + " - " +
                    (b.to() != null ? String.valueOf(b.to()) : "∞");
            result.put(key, b.docCount());
        });
        return result;
    }
}