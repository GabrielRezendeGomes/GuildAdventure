package com.GuildAdventure.demo.Domain.Model.Aventura.entities;

import com.GuildAdventure.demo.Domain.Model.Audit.entities.user_role_id;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class participacao_missao_id implements Serializable {
    private Long aventureiroId;
    private Long missaoId;



}
