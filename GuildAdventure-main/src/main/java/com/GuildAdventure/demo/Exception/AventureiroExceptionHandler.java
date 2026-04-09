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
         ErroResponse erro = ErroResponse.builder()
                 .mensagem(ex.get_tipo().getMensagem())
                 .detalhes(ex.get_detalhes())
                 .timestamp(System.currentTimeMillis())
                 .build();
         return  ResponseEntity.status(ex.get_tipo().getStatus()).body(erro);
    }

}