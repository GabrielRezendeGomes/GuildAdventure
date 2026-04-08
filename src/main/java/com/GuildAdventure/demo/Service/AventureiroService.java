package com.GuildAdventure.demo.Service;

import com.GuildAdventure.demo.Dto.AventureiroRequest;
import com.GuildAdventure.demo.Dto.AventureiroResponse;
import com.GuildAdventure.demo.Dto.IMapper;
import com.GuildAdventure.demo.Repository.IAventureiroRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AventureiroService
{
    @Autowired
    private IMapper _mapper;

    private final IAventureiroRepository _repository;

    public boolean ValidateAventureiro(AventureiroRequest aventureiro)
    {

    }

}
