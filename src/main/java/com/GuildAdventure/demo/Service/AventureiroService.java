package com.GuildAdventure.demo.Service;

import com.GuildAdventure.demo.Domain.Model.Aventura.entities.AventureiroEntity;

import com.GuildAdventure.demo.Dto.CreateAventureiroRequest;
import com.GuildAdventure.demo.Dto.IMapper;
import com.GuildAdventure.demo.Repository.IAventureiroRepository;
import com.GuildAdventure.demo.Service.Validations.AventureiroValidations.IAventureiroValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AventureiroService
{
    @Autowired
    private IMapper _mapper;

    private final IAventureiroRepository _repository;
    private final List<IAventureiroValidate> validadores;
    private final IMapper mapper;

    public AventureiroService(IAventureiroRepository _repository,List<IAventureiroValidate> validadores,IMapper mapper )
    {
        this._repository = _repository;
        this.validadores = validadores;
        this.mapper = mapper;
    }

    public AventureiroEntity criar(CreateAventureiroRequest request)
    {
        validarAventureiro(request);
        AventureiroEntity aventureiroParaSalvar = mapper.toEntity(request);

        return _repository.save(aventureiroParaSalvar);

    }


    private void validarAventureiro(CreateAventureiroRequest aventureiro) {
        validadores.forEach(v -> v.validar(aventureiro));
    }

}
