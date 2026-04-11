package com.GuildAdventure.demo;

import com.GuildAdventure.demo.Dto.MissaoMetricasResponse;
import com.GuildAdventure.demo.Dto.RankingResponse;
import com.GuildAdventure.demo.Repository.IAventureiroRepository;
import com.GuildAdventure.demo.Repository.IMissaoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ConsultasRepositoryTest {

    @Autowired
    private IAventureiroRepository aventureiroRepository;

    @Autowired
    private IMissaoRepository missaoRepository;

    @Test
    public void testBuscaParcial() {
        var resultado = aventureiroRepository.buscaParcial("Heroi", PageRequest.of(0, 10));
        assertNotNull(resultado);
    }

    @Test
    public void testBuscarAventureiroCompleto() {
        var resultado = aventureiroRepository.buscarAventureiroCompleto(1L);
    }

    @Test
    public void testGerarRankingParticipacao() {
        List<RankingResponse> ranking = aventureiroRepository.gerarRankingParticipacao();
        assertNotNull(ranking);
    }

    @Test
    public void testGerarRelatorioMetricas() {
        List<MissaoMetricasResponse> metricas = missaoRepository.gerarRelatorioMetricas();
        assertNotNull(metricas);
    }
}