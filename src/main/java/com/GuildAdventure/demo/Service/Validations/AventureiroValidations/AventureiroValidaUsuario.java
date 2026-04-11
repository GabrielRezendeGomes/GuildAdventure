package com.GuildAdventure.demo.Service.Validations.AventureiroValidations;

import com.GuildAdventure.demo.Dto.CreateAventureiroRequest;
import com.GuildAdventure.demo.Exception.AventureiroExceptionService;
import com.GuildAdventure.demo.Exception.ErroCatalogo;
import com.GuildAdventure.demo.Repository.IUsuarioRepository;
import org.springframework.stereotype.Component;

@Component
public class AventureiroValidaUsuario implements IAventureiroValidate {

    private final IUsuarioRepository usuarioRepository;
    private final AventureiroExceptionService exceptionService;

    public AventureiroValidaUsuario(AventureiroExceptionService exceptionService, IUsuarioRepository usuarioRepository){
        this.exceptionService = exceptionService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void validar(CreateAventureiroRequest request) {
        if(request.getUsuario_cadastrado_id() == 0) {
            exceptionService.lancar(ErroCatalogo.AVENTUREIRO_DEVE_TER_UM_USUARIO);
        }
        if(!usuarioRepository.existsById(request.getUsuario_cadastrado_id())){
            exceptionService.lancar(ErroCatalogo.USUARIO_INVALIDO);
        }
    }
}