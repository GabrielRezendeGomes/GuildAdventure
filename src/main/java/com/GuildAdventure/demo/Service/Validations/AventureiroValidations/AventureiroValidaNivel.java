package com.GuildAdventure.demo.Service.Validations.AventureiroValidations;


import com.GuildAdventure.demo.Dto.CreateAventureiroRequest;
import com.GuildAdventure.demo.Exception.AventureiroExceptionService;
import com.GuildAdventure.demo.Exception.ErroCatalogo;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class AventureiroValidaNivel implements IAventureiroValidate {

    private final AventureiroExceptionService exceptionService;

    public AventureiroValidaNivel(AventureiroExceptionService exceptionService) {

        this.exceptionService = exceptionService;
    }

    @Override
    public void validar(CreateAventureiroRequest aventureiro) {
        if (aventureiro.getNivel() < 1) {
            exceptionService.lancar(ErroCatalogo.NIVEL_DE_AVENTUREIRO_MENOR_QUE_UM);
        } ;

    }
}
