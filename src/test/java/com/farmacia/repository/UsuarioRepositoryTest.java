package com.farmacia.repository;

import com.farmacia.Enum.Rol;
import com.farmacia.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ActiveProfiles("test")
class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuario1;
    private Usuario usuario2;
    private Usuario usuario3;

    @BeforeEach
    void setUp() {
        usuarioRepository.deleteAll();

        usuario1 = new Usuario("Juan", "Pérez", "juan@example.com", "1234567890", "password", Rol.CLIENTE);
        usuario1.setActivo(true);
        usuario1.setFechaRegistro(LocalDateTime.now());
        usuario1.setCiudad("Bogotá");
        usuario1.setEstado("Cundinamarca");
        usuario1.setCodigoPostal("110111");

        usuario2 = new Usuario("María", "García", "maria@example.com", "0987654321", "password", Rol.CLIENTE);
        usuario2.setActivo(true);
        usuario2.setFechaRegistro(LocalDateTime.now());
        usuario2.setCiudad("Medellín");
        usuario2.setEstado("Antioquia");
        usuario2.setCodigoPostal("050001");

        usuario3 = new Usuario("Admin", "User", "admin@example.com", "1111111111", "password", Rol.ADMIN);
        usuario3.setActivo(true);
        usuario3.setFechaRegistro(LocalDateTime.now());

        usuarioRepository.save(usuario1);
        usuarioRepository.save(usuario2);
        usuarioRepository.save(usuario3);
    }

    // ============ Tests de Búsqueda Básica ============

    @Test
    void testFindByEmail() {
        Optional<Usuario> resultado = usuarioRepository.findByEmail("juan@example.com");
        
        assertTrue(resultado.isPresent());
        assertEquals("Juan", resultado.get().getNombre());
    }

    @Test
    void testFindByEmailNoEncontrado() {
        Optional<Usuario> resultado = usuarioRepository.findByEmail("noexiste@example.com");
        
        assertFalse(resultado.isPresent());
    }

    @Test
    void testExistsByEmail() {
        boolean existe = usuarioRepository.existsByEmail("juan@example.com");
        
        assertTrue(existe);
    }

    @Test
    void testExistsByEmailNoExiste() {
        boolean existe = usuarioRepository.existsByEmail("noexiste@example.com");
        
        assertFalse(existe);
    }

    // ============ Tests de Búsqueda por Rol ============

    @Test
    void testFindByRol() {
        List<Usuario> clientes = usuarioRepository.findByRol(Rol.CLIENTE);
        
        assertEquals(2, clientes.size());
    }

    @Test
    void testFindByRolAdmin() {
        List<Usuario> admins = usuarioRepository.findByRol(Rol.ADMIN);
        
        assertEquals(1, admins.size());
    }

    // ============ Tests de Búsqueda por Estado Activo ============

    @Test
    void testFindByActivo() {
        List<Usuario> activos = usuarioRepository.findByActivo(true);
        
        assertEquals(3, activos.size());
    }

    @Test
    void testFindByActivoFalse() {
        usuarioRepository.deleteAll();
        
        Usuario usuarioInactivo = new Usuario("Test", "Test", "test@example.com", "1234567890", "password", Rol.CLIENTE);
        usuarioInactivo.setActivo(false);
        usuarioRepository.save(usuarioInactivo);

        List<Usuario> inactivos = usuarioRepository.findByActivo(false);
        
        assertEquals(1, inactivos.size());
    }

    // ============ Tests de Búsqueda Combinada ============

    @Test
    void testFindByRolAndActivo() {
        List<Usuario> clientesActivos = usuarioRepository.findByRolAndActivo(Rol.CLIENTE, true);
        
        assertEquals(2, clientesActivos.size());
    }

    // ============ Tests de Conteo ============

    @Test
    void testCountByRol() {
        long countClientes = usuarioRepository.countByRol(Rol.CLIENTE);
        
        assertEquals(2, countClientes);
    }

    @Test
    void testCountByRolAdmin() {
        long countAdmins = usuarioRepository.countByRol(Rol.ADMIN);
        
        assertEquals(1, countAdmins);
    }

    // ============ Tests de Búsqueda Avanzada ============

    @Test
    void testBuscarPorNombre() {
        List<Usuario> resultados = usuarioRepository.buscarPorNombreOApellido("Juan");
        
        assertTrue(resultados.size() > 0);
        assertTrue(resultados.stream().anyMatch(u -> u.getNombre().contains("Juan")));
    }

    @Test
    void testBuscarPorApellido() {
        List<Usuario> resultados = usuarioRepository.buscarPorNombreOApellido("Pérez");
        
        assertTrue(resultados.size() > 0);
        assertTrue(resultados.stream().anyMatch(u -> u.getApellido().contains("Pérez")));
    }

    @Test
    void testBuscarPorTerminoVacio() {
        List<Usuario> resultados = usuarioRepository.buscarPorNombreOApellido("NoExiste");
        
        assertEquals(0, resultados.size());
    }

    @Test
    void testBuscarCaseSensitive() {
        List<Usuario> resultados1 = usuarioRepository.buscarPorNombreOApellido("juan");
        List<Usuario> resultados2 = usuarioRepository.buscarPorNombreOApellido("JUAN");
        
        assertEquals(resultados1.size(), resultados2.size());
    }

    // ============ Tests de Campos de Dirección ============

    @Test
    void testObtenerUsuarioConDireccion() {
        Optional<Usuario> resultado = usuarioRepository.findByEmail("juan@example.com");
        
        assertTrue(resultado.isPresent());
        assertEquals("Bogotá", resultado.get().getCiudad());
        assertEquals("110111", resultado.get().getCodigoPostal());
    }

    // ============ Tests de Actualización ============

    @Test
    void testActualizarUsuario() {
        Optional<Usuario> optUsuario = usuarioRepository.findByEmail("juan@example.com");
        assertTrue(optUsuario.isPresent());
        
        Usuario usuario = optUsuario.get();
        usuario.setCiudad("Cali");
        usuario.setCodigoPostal("760001");
        usuarioRepository.save(usuario);

        Optional<Usuario> actualizado = usuarioRepository.findByEmail("juan@example.com");
        assertTrue(actualizado.isPresent());
        assertEquals("Cali", actualizado.get().getCiudad());
    }

    // ============ Tests de Eliminación ============

    @Test
    void testEliminarUsuario() {
        long countAntes = usuarioRepository.count();
        
        usuarioRepository.deleteAll();
        
        long countDespues = usuarioRepository.count();
        assertEquals(0, countDespues);
        assertTrue(countAntes > countDespues);
    }
}