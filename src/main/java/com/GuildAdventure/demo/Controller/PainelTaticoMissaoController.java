package com.GuildAdventure.demo.Controller;

import com.GuildAdventure.demo.Domain.Model.Operacoes.entities.PainelTaticoMissaoEntity;
import com.GuildAdventure.demo.Service.PainelTaticoMissaoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/missoes")
public class PainelTaticoMissaoController {

    private final PainelTaticoMissaoService service;

    public PainelTaticoMissaoController(PainelTaticoMissaoService service) {
        this.service = service;
    }

    @GetMapping("/top15dias")
    public List<PainelTaticoMissaoEntity> getTop15Dias() {
        return service.getTop15Dias();
    }
}