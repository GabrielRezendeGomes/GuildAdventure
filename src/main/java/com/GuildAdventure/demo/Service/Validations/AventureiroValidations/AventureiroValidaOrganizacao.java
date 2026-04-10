package com.GuildAdventure.demo.Service.Validations.AventureiroValidations;


import com.GuildAdventure.demo.Dto.CreateAventureiroRequest;
import com.GuildAdventure.demo.Exception.AventureiroExceptionService;
import com.GuildAdventure.demo.Exception.ErroCatalogo;
import com.GuildAdventure.demo.Repository.IOrganizacaoRepository;
import org.springframework.stereotype.Component;

@Component
public class AventureiroValidaOrganizacao implements IAventureiroValidate {

    private final IOrganizacaoRepository organizacaoRepository;

    private final AventureiroExceptionService exceptionService;

    public AventureiroValidaOrganizacao(AventureiroExceptionService exceptionService, IOrganizacaoRepository organizacaoRepository){
        this.exceptionService = exceptionService;
        this.organizacaoRepository = organizacaoRepository;
    }

    @Override
    public void validar(CreateAventureiroRequest request)
    {
        if(request.getOrganizacao_id() == 0)
        {
            exceptionService.lancar(ErroCatalogo.AVENTUREIRO_DEVE_TER_UM_USUARIO);
        }

        if(!organizacaoRepository.existsById(request.getOrganizacao_id()))
        {
            exceptionService.lancar(ErroCatalogo.USUARIO_INVALIDO);
        };


    }

}
