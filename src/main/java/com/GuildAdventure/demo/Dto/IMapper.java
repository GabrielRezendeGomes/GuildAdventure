package com.GuildAdventure.demo.Dto;

import com.GuildAdventure.demo.Domain.Model.Aventura.entities.AventureiroEntity;
import com.GuildAdventure.demo.Domain.Model.Aventura.entities.MissaoEntity;
import com.GuildAdventure.demo.Domain.Model.Aventura.entities.ParticipacaoMissao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IMapper
{

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "usuarioCadastro", ignore = true)
    @Mapping(target = "companheiro", ignore = true)
    @Mapping(target = "missoes", ignore = true)
    @Mapping(target = "organizacao", ignore = true)
    AventureiroEntity toEntity(CreateAventureiroRequest request);

    @Mapping(target = "id", source = "aventureiro.id")
    @Mapping(target = "nome", source = "aventureiro.nome")
    @Mapping(target = "classe", source = "aventureiro.classe")
    @Mapping(target = "nivel", source = "aventureiro.nivel")
    @Mapping(target = "status", source = "aventureiro.ativo")
    @Mapping(target = "companheiro", source = "aventureiro.companheiro") //
    @Mapping(target = "quantidadeTotalDeMissoes", source = "totalMissoes")
    @Mapping(target = "ultimaMissao", source = "ultima")
    AventureiroResponse toResponse(
            AventureiroEntity aventureiro,
            Long totalMissoes,
            MissaoEntity ultima
    );


    @Mapping(target = "status" , source = "ativo")
    AventureiroResponseSimples toSimpleResponse(AventureiroEntity aventureiro);

    @Mapping(target = "participantes", source = "aventureirosInscritos")
    MissaoResponse toMissaoResponse(MissaoEntity missao);

    @Mapping(target = "aventureiroId", source = "aventureiroid.id")
    @Mapping(target = "nomeAventureiro", source = "aventureiroid.nome")
    ParticipacaoMissaoResponse toParticipacaoResponse(ParticipacaoMissao participacao);



}
