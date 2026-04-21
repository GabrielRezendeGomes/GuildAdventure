package com.GuildAdventure.demo.Handler;

import com.GuildAdventure.demo.Domain.Model.Aventura.Events.AventureiroAlteradoEvent;
import com.GuildAdventure.demo.Repository.IParicipacaoRepository;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Component
public class PainelTaticoCacheHandler {

    private final IParicipacaoRepository participacaoRepository;
    private final CacheManager cacheManager;

    public PainelTaticoCacheHandler(IParicipacaoRepository participacaoRepository, CacheManager cacheManager) {
        this.participacaoRepository = participacaoRepository;
        this.cacheManager = cacheManager;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onAventureiroAlterado(AventureiroAlteradoEvent event) {


        List<Long> missoesAfetadas = participacaoRepository.findMissoesByAventureiro(event.aventureiroId());


        Cache cache = cacheManager.getCache("painelTatico");

        if (cache != null) {

            for (Long missaoId : missoesAfetadas) {
                cache.evict(missaoId);
                System.out.println("Cache da Missão " + missaoId + " invalidado por alteração de aventureiro.");
            }
        }
    }

}
