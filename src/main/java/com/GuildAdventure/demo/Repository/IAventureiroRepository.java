package com.GuildAdventure.demo.Repository;

import com.GuildAdventure.demo.Domain.Model.Aventura.entities.AventureiroEntity;
import com.GuildAdventure.demo.Dto.AventureiroResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface IAventureiroRepository extends JpaRepository<AventureiroEntity, Long>
{

        @Query("SELECT a FROM AventureiroEntity a WHERE LOWER(a.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
        List<AventureiroEntity> buscaParcial(@Param("nome") String nome, Pageable pageable);

        @Query("SELECT a FROM AventureiroEntity a WHERE a.ativo = :status")
        List<AventureiroEntity> buscaPorStatus(@Param("status") String status, Pageable pageable);

        @Query("SELECT a FROM AventureiroEntity a WHERE LOWER(a.classe) LIKE LOWER(CONCAT('%', :classe, '%'))")
        List<AventureiroEntity> buscaPorClasse(@Param("classe") String classe, Pageable pageable);

        @Query("SELECT a FROM AventureiroEntity a WHERE a.nivel >= :nivel")
        List<AventureiroEntity> buscarPorNivelMinimo(@Param("nivel") int nivel, Pageable pageable);


        @Query("""
        SELECT new com.GuildAdventure.demo.Dto.AventureiroResponse(
            a.id,
            a.nome,
            a.classe,
            a.nivel,
            a.ativo,
            c,
            (SELECT COUNT(p) FROM Participacao_missao p WHERE p.aventureiroid = a)
        )
        FROM AventureiroEntity a
        LEFT JOIN a.companheiro c
        WHERE a.id = :id
        """)
        AventureiroResponse buscarAventureiroCompleto(@Param("id") Long id);
    }

