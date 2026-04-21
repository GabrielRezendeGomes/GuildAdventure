package com.GuildAdventure.demo.Service;

import com.GuildAdventure.demo.Domain.Model.Operacoes.entities.PainelTaticoMissaoEntity;
import com.GuildAdventure.demo.Repository.IPainelTaticoMissaoRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class PainelTaticoMissaoService {

    private final IPainelTaticoMissaoRepository repository;

    public PainelTaticoMissaoService(IPainelTaticoMissaoRepository repository) {
        this.repository = repository;
    }


    @Cacheable(value = "painelTatico", key = "'top15Dias'")
    public List<PainelTaticoMissaoEntity> getTop15Dias() {
        OffsetDateTime quinzeDiasAtras = OffsetDateTime.now().minusDays(15);
        System.out.println("### BUSCANDO NO BANCO DE DADOS (VIEW) ###");
        return repository.buscarTop10Recentes(quinzeDiasAtras);
    }
}