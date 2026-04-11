package com.GuildAdventure.demo.Controller;

import com.GuildAdventure.demo.Domain.Model.Aventura.entities.AventureiroEntity;
import com.GuildAdventure.demo.Domain.Model.Aventura.entities.CompanheiroEntity;
import com.GuildAdventure.demo.Dto.AventureiroResponse;
import com.GuildAdventure.demo.Dto.RankingResponse;
import com.GuildAdventure.demo.Service.AventureiroService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Aventureiros")
public class AventureiroController {

    private final AventureiroService service;

    public AventureiroController(AventureiroService service) {
        this.service = service;
    }

    // 1. Criação de Aventureiro
    @PostMapping
    public ResponseEntity<AventureiroEntity> salvar(@RequestBody AventureiroEntity aventureiro) {
        return ResponseEntity.ok(service.salvar(aventureiro));
    }

    // 2. Busca por Nome (Parcial, Ordenada e Paginável)
    @GetMapping("/busca")
    public ResponseEntity<List<AventureiroEntity>> buscarPorNome(
            @RequestParam String nome,
            Pageable pageable) {
        return ResponseEntity.ok(service.buscarParcial(nome, pageable));
    }

    // 3. Listagem com Filtros (Status, Classe, Nível)
    @GetMapping("/filtro/status")
    public ResponseEntity<List<AventureiroEntity>> listarPorStatus(
            @RequestParam boolean ativo,
            Pageable pageable) {
        return ResponseEntity.ok(service.listarPorStatus(ativo, pageable));
    }

    @GetMapping("/filtro/classe")
    public ResponseEntity<List<AventureiroEntity>> listarPorClasse(
            @RequestParam String classe,
            Pageable pageable) {
        return ResponseEntity.ok(service.listarPorClasse(classe, pageable));
    }

    @GetMapping("/filtro/nivel")
    public ResponseEntity<List<AventureiroEntity>> listarPorNivelMinimo(
            @RequestParam int nivel,
            Pageable pageable) {
        return ResponseEntity.ok(service.listarPorNivelMinimo(nivel, pageable));
    }

    // 4. Visualização Completa do Aventureiro (Perfil + Companheiro + Missões)
    @GetMapping("/{id}")
    public ResponseEntity<AventureiroResponse> obterPerfilCompleto(@PathVariable Long id) {
        return ResponseEntity.ok(service.obterPerfilCompleto(id));
    }

    // 5. Ranking de Participação
    @GetMapping("/ranking")
    public ResponseEntity<List<RankingResponse>> gerarRanking() {
        return ResponseEntity.ok(service.gerarRanking());
    }

    // 6. Atualizar Status (Ativo/Inativo)
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> alterarStatus(@PathVariable Long id, @RequestParam boolean ativo) {
        service.alterarStatus(id, ativo);
        return ResponseEntity.noContent().build();
    }

    // 7. Gerenciar Companheiro (Adicionar/Alterar/Remover)
    @PutMapping("/{id}/companheiro")
    public ResponseEntity<AventureiroEntity> gerenciarCompanheiro(
            @PathVariable Long id,
            @RequestBody(required = false) CompanheiroEntity companheiro) {
        return ResponseEntity.ok(service.gerenciarCompanheiro(id, companheiro));
    }

    // 8. Deleção de Aventureiro
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}