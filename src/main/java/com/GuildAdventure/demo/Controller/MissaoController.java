package com.GuildAdventure.demo.Controller;

import com.GuildAdventure.demo.Domain.Model.Aventura.Enums.NivelDePerigoTypeEnum;
import com.GuildAdventure.demo.Domain.Model.Aventura.Enums.StatusMissaoEnum;
import com.GuildAdventure.demo.Domain.Model.Aventura.entities.MissaoEntity;
import com.GuildAdventure.demo.Dto.AdicionarAventureiroRequest;
import com.GuildAdventure.demo.Dto.CreateMissaoRequest;
import com.GuildAdventure.demo.Dto.MissaoMetricasResponse;
import com.GuildAdventure.demo.Dto.MissaoResponse;
import com.GuildAdventure.demo.Service.MissaoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@RestController
@RequestMapping("/missoes-operacionais")
public class MissaoController {

    private final MissaoService service;

    public MissaoController(MissaoService service) {
        this.service = service;
    }

    @GetMapping("/por-status")
    public ResponseEntity<Page<MissaoResponse>> buscarPorStatus(
            @RequestParam StatusMissaoEnum status,
            @PageableDefault(sort = "dataCriacao", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<MissaoResponse> missoes = service.buscarPorStatus(status, pageable);
        return ResponseEntity.ok(missoes);
    }

    @GetMapping("/por-perigo")
    public ResponseEntity<Page<MissaoResponse>> buscarPorNivelPerigo(
            @RequestParam NivelDePerigoTypeEnum nivelPerigo,
            @PageableDefault(sort = "dataCriacao", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<MissaoResponse> missoes = service.buscarPorNivelPerigo(nivelPerigo, pageable);
        return ResponseEntity.ok(missoes);
    }

    @GetMapping("/por-data")
    public ResponseEntity<Page<MissaoResponse>> buscarPorDataCriacao(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            Pageable pageable) {

        OffsetDateTime inicioCompleto = dataInicio.atStartOfDay().atOffset(ZoneOffset.UTC);

        OffsetDateTime fimCompleto = dataFim.atTime(LocalTime.MAX).atOffset(ZoneOffset.UTC);

        Page<MissaoResponse> missoes = service.buscarPorDataCriacao(inicioCompleto, fimCompleto, pageable);

        return ResponseEntity.ok(missoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MissaoResponse> buscarDetalhes(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarDetalhes(id));
    }

    @GetMapping("/relatorio-metricas")
    public ResponseEntity<List<MissaoMetricasResponse>> gerarRelatorio() {
        return ResponseEntity.ok(service.gerarRelatorio());
    }

    @PostMapping
    public ResponseEntity<MissaoResponse> criarMissao(@Valid @RequestBody CreateMissaoRequest request) {

        MissaoResponse response = service.criarMissao(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{missaoId}/aventureiros/{aventureiroId}")
    public ResponseEntity<MissaoResponse> removerAventureiro(
            @PathVariable Long missaoId,
            @PathVariable Long aventureiroId) {


        MissaoResponse response = service.removerAventureiroDaMissao(missaoId, aventureiroId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{missaoId}/aventureiros")
    public ResponseEntity<MissaoResponse> adicionarAventureiro(
            @PathVariable Long missaoId,
            @Valid @RequestBody AdicionarAventureiroRequest request) {


        MissaoResponse response = service.adicionarAventureiroNaMissao(missaoId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}