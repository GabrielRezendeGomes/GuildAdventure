package com.GuildAdventure.demo.Domain.Model.Aventura.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ParticipacaoMissaoId implements Serializable {
    private Long aventureiroId;
    private Long missaoId;



}
