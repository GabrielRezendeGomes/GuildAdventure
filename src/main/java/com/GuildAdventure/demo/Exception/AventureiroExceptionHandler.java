package com.GuildAdventure.demo.Exception;
import com.GuildAdventure.demo.Dto.ErroResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AventureiroExceptionHandler
{

    @ExceptionHandler(AventureiroException.class)
    public ResponseEntity<ErroResponse> handleAventureiro(AventureiroException ex)
    {
        ErroResponse erro = new ErroResponse(ex.getTipo().getMensagem(),
                ex.getDetalhes(),
                System.currentTimeMillis());
        return ResponseEntity
                 .status(ex.getTipo().getStatus())
            .body(erro);
    }


}