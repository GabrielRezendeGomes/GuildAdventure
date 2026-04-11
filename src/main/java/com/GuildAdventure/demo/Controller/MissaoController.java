package com.GuildAdventure.demo.Controller;

import com.GuildAdventure.demo.Domain.Model.Aventura.Enums.NivelDePerigoTypeEnum;
import com.GuildAdventure.demo.Domain.Model.Aventura.Enums.StatusMissaoEnum;
import com.GuildAdventure.demo.Domain.Model.Aventura.entities.MissaoEntity;
import com.GuildAdventure.demo.Dto.MissaoMetricasResponse;
import com.GuildAdventure.demo.Dto.MissaoResponse;
import com.GuildAdventure.demo.Service.MissaoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("/missoes-operacionais")
public class MissaoController {

    private final MissaoService service;

    public MissaoController(MissaoService service) {
        this.service = service;
    }

    @GetMapping("/filtro")
    public ResponseEntity<Page<MissaoEntity>> buscarComFiltros(
            @RequestParam(required = false) StatusMissaoEnum status,
            @RequestParam(required = false) NivelDePerigoTypeEnum nivelPerigo,
            @RequestParam(required = false) OffsetDateTime dataInicio,
            @RequestParam(required = false) OffsetDateTime dataFim,
            Pageable pageable) {

        return ResponseEntity.ok(service.buscarComFiltros(status, nivelPerigo, dataInicio, dataFim, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MissaoResponse> buscarDetalhes(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarDetalhes(id));
    }

    @GetMapping("/relatorio-metricas")
    public ResponseEntity<List<MissaoMetricasResponse>> gerarRelatorio() {
        return ResponseEntity.ok(service.gerarRelatorio());
    }
}