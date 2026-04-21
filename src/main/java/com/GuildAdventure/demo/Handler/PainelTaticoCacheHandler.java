package com.GuildAdventure.demo.Handler;

import com.GuildAdventure.demo.Domain.Model.Aventura.Events.AventureiroAlteradoEvent;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class PainelTaticoCacheHandler {

    private final CacheManager cacheManager;

    public PainelTaticoCacheHandler(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onAventureiroAlterado(AventureiroAlteradoEvent event) {

        Cache cache = cacheManager.getCache("painelTatico");

        if (cache != null) {
            cache.evict("top15Dias");

            System.out.println("Cache global 'top15Dias' invalidado devido à alteração do aventureiro ID: " + event.aventureiroId());
        }
    }
}