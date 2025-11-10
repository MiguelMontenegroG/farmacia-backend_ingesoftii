package com.farmacia;

import com.farmacia.Enum.Rol;
import com.farmacia.model.Usuario;
import com.farmacia.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class UsuarioRepoTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void testCrearUsuariosYBuscarPorEmail() {
        // Arrange - Crear usuarios
        Usuario usuario1 = new Usuario("Juan", "Pérez", "juan@example.com", "123456789", "password123", Rol.CLIENTE);
        usuario1.setDireccion("Calle 123");
        usuario1.setActivo(true);

        Usuario usuario2 = new Usuario("María", "García", "maria@example.com", "987654321", "password456", Rol.ADMINISTRADOR);
        usuario2.setDireccion("Avenida 456");
        usuario2.setActivo(true);

        Usuario usuario3 = new Usuario("Carlos", "López", "carlos@example.com", "555666777", "password789", Rol.CLIENTE);
        usuario3.setDireccion("Plaza 789");
        usuario3.setActivo(false);

        // Act - Guardar usuarios
        usuarioRepository.saveAll(java.util.List.of(usuario1, usuario2, usuario3));

        // Assert - Verificar que se guardaron y buscar por email
        Optional<Usuario> found1 = usuarioRepository.findByEmail("juan@example.com");
        assertThat(found1).isPresent();
        assertThat(found1.get().getNombre()).isEqualTo("Juan");
        assertThat(found1.get().getRol()).isEqualTo(Rol.CLIENTE);

        Optional<Usuario> found2 = usuarioRepository.findByEmail("maria@example.com");
        assertThat(found2).isPresent();
        assertThat(found2.get().getRol()).isEqualTo(Rol.ADMINISTRADOR);

        Optional<Usuario> notFound = usuarioRepository.findByEmail("noexiste@example.com");
        assertThat(notFound).isEmpty();
    }

    @Test
    void testExistsByEmail() {
        // Arrange
        Usuario usuario = new Usuario("Ana", "Martínez", "ana@example.com", "111222333", "password000", Rol.CLIENTE);
        usuarioRepository.save(usuario);

        // Act & Assert
        assertThat(usuarioRepository.existsByEmail("ana@example.com")).isTrue();
        assertThat(usuarioRepository.existsByEmail("inexistente@example.com")).isFalse();
    }
}