package com.GuildAdventure.demo.Controller;

import com.GuildAdventure.demo.Domain.Model.Aventura.Enums.ClassTypeEnum;
import com.GuildAdventure.demo.Domain.Model.Aventura.entities.AventureiroEntity;
import com.GuildAdventure.demo.Domain.Model.Aventura.entities.CompanheiroEntity;
import com.GuildAdventure.demo.Dto.*;
import com.GuildAdventure.demo.Service.AventureiroService;
import org.springframework.data.domain.Page;
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

    @PostMapping
    public ResponseEntity<AventureiroResponseSimples> salvar(@RequestBody CreateAventureiroRequest request) {
        return ResponseEntity.ok(service.salvar(request));
    }

    @GetMapping("/busca")
    public ResponseEntity<Page<AventureiroResponseSimples>> buscarPorNome(
            @RequestParam String nome,
            Pageable pageable) {
        return ResponseEntity.ok(service.buscarParcial(nome, pageable));
    }

    @GetMapping("/filtro")
    public ResponseEntity<Page<AventureiroResponseSimples>> listarComFiltros(
            @RequestParam(required = false) Boolean ativo,
            @RequestParam(required = false) ClassTypeEnum classe,
            @RequestParam(required = false) Integer nivel,
            Pageable pageable) {
        return ResponseEntity.ok(service.buscarComFiltros(ativo, classe, nivel, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AventureiroResponse> obterPerfilCompleto(@PathVariable Long id) {
        return ResponseEntity.ok(service.obterPerfilCompleto(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> alterarStatus(@PathVariable Long id, @RequestParam boolean ativo) {
        service.alterarStatus(id, ativo);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<AventureiroResponseSimples> atualizar(@PathVariable Long id, @RequestBody UpdateAventureiroRequest request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @PutMapping("/{id}/companheiro")
    public ResponseEntity<AventureiroResponse> atualizarCompanheiro(
            @PathVariable Long id,
            @RequestBody CompanheiroRequest companheiroRequest) {
        return ResponseEntity.ok(service.atualizarCompanheiro(id, companheiroRequest));
    }

    @DeleteMapping("/{id}/companheiro")
    public ResponseEntity<Void> removerCompanheiro(@PathVariable Long id) {
        service.removerCompanheiro(id);
        return ResponseEntity.noContent().build();
    }
}