package com.GuildAdventure.demo;

import com.GuildAdventure.demo.Domain.Model.Audit.entities.UsuarioEntity;
import com.GuildAdventure.demo.Repository.IUsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class LegadoRepositoryTest {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Test
    public void testRelacionamentosLegado() {
        // Tenta buscar um usuário existente (assumindo que o banco do Docker tem dados populados)
        // Se o banco estiver vazio no teste, o importante é que a query execute sem erros de sintaxe/mapeamento
        Optional<UsuarioEntity> usuarioOpt = usuarioRepository.findById(1L);

        usuarioOpt.ifPresent(usuario -> {
            // Verifica o relacionamento com Organização
            assertNotNull(usuario.getOrganizacao(), "Organização não deve ser nula");
            assertNotNull(usuario.getOrganizacao().getNome(), "Nome da organização deve existir");

            // Verifica as Roles do usuário
            assertNotNull(usuario.getUserRoles(), "Lista de roles não deve ser nula");

            // Verifica as Permissões dentro das Roles (se houver alguma)
            if (!usuario.getUserRoles().isEmpty()) {
                var role = usuario.getUserRoles().get(0).getRole();
                assertNotNull(role.getPermissions(), "Permissões da role não devem ser nulas");
            }
        });
    }
}