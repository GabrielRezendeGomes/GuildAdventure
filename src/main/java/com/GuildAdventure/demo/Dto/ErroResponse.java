package com.GuildAdventure.demo.Dto;

import lombok.*;

import java.util.List;
public class ErroResponse {
    private String mensagem;
    private List<String> detalhes;
    private long timestamp;

    public ErroResponse() {
    }


    public ErroResponse(String mensagem, List<String> detalhes, long timestamp) {
        this.mensagem = mensagem;
        this.detalhes = detalhes;
        this.timestamp = timestamp;
    }


    public String getMensagem() { return mensagem; }
    public List<String> getDetalhes() { return detalhes; }
    public long getTimestamp() { return timestamp; }

    public void setMensagem(String mensagem) { this.mensagem = mensagem; }
    public void setDetalhes(List<String> detalhes) { this.detalhes = detalhes; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}