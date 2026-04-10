package com.GuildAdventure.demo.Controller;

import com.GuildAdventure.demo.Domain.Model.Aventura.entities.AventureiroEntity;
import com.GuildAdventure.demo.Dto.CreateAventureiroRequest;
import com.GuildAdventure.demo.Service.AventureiroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/Aventureiros")
public class AventureirosController
{
    private final AventureiroService service;

    public AventureirosController(AventureiroService service)
    {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AventureiroEntity> criar(@RequestBody CreateAventureiroRequest request)
    {
        AventureiroEntity aventureiro = service.criar(request);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(aventureiro.getId())
                .toUri();
        return ResponseEntity.created(uri).body(aventureiro);
    }
}
