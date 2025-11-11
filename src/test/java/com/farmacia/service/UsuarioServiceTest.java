package com.farmacia.service;

import com.farmacia.Enum.Rol;
import com.farmacia.dto.ActualizarPerfilDTO;
import com.farmacia.dto.ActualizarDireccionDTO;
import com.farmacia.dto.ActualizarUsuarioAdminDTO;
import com.farmacia.dto.UsuarioDetalleDTO;
import com.farmacia.model.Usuario;
import com.farmacia.repository.UsuarioRepository;
import com.farmacia.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.farmacia.security.JwtUtil;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private Usuario usuarioTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        usuarioTest = new Usuario("Juan", "Pérez", "juan@example.com", "1234567890", "password", Rol.CLIENTE);
        usuarioTest.setId("123");
        usuarioTest.setActivo(true);
        usuarioTest.setFechaRegistro(LocalDateTime.now());
        usuarioTest.setCiudad("Bogotá");
        usuarioTest.setEstado("Cundinamarca");
        usuarioTest.setCodigoPostal("110111");
        usuarioTest.setDireccion("Calle 1 #2-3");
        usuarioTest.setTotalCompras(5);
        usuarioTest.setMontoTotalCompras(150000.0);
    }

    // ============ Tests de Actualización de Perfil ============

    @Test
    void testActualizarPerfilExitoso() {
        ActualizarPerfilDTO perfilDTO = new ActualizarPerfilDTO("Carlos", "López", "3156789012");
        
        when(usuarioRepository.findById("123")).thenReturn(Optional.of(usuarioTest));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioTest);

        Usuario resultado = usuarioService.actualizarPerfil("123", perfilDTO);

        assertNotNull(resultado);
        assertEquals("Carlos", resultado.getNombre());
        assertEquals("López", resultado.getApellido());
        assertEquals("3156789012", resultado.getTelefono());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void testActualizarPerfilUsuarioNoEncontrado() {
        ActualizarPerfilDTO perfilDTO = new ActualizarPerfilDTO("Carlos", "López", "3156789012");
        
        when(usuarioRepository.findById("999")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> usuarioService.actualizarPerfil("999", perfilDTO));
    }

    // ============ Tests de Actualización de Dirección ============

    @Test
    void testActualizarDireccionExitoso() {
        ActualizarDireccionDTO direccionDTO = new ActualizarDireccionDTO(
            "Calle 5 #10-15", "Medellín", "Antioquia", "050001"
        );
        
        when(usuarioRepository.findById("123")).thenReturn(Optional.of(usuarioTest));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioTest);

        Usuario resultado = usuarioService.actualizarDireccion("123", direccionDTO);

        assertNotNull(resultado);
        assertEquals("Medellín", resultado.getCiudad());
        assertEquals("050001", resultado.getCodigoPostal());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void testActualizarDireccionUsuarioNoEncontrado() {
        ActualizarDireccionDTO direccionDTO = new ActualizarDireccionDTO(
            "Calle 5 #10-15", "Medellín", "Antioquia", "050001"
        );
        
        when(usuarioRepository.findById("999")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> usuarioService.actualizarDireccion("999", direccionDTO));
    }

    // ============ Tests de Listado de Usuarios (Admin) ============

    @Test
    void testListarUsuarios() {
        Usuario usuario2 = new Usuario("María", "García", "maria@example.com", "0987654321", "password", Rol.CLIENTE);
        usuario2.setId("124");
        
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuarioTest, usuario2));

        List<UsuarioDetalleDTO> resultado = usuarioService.listarUsuarios();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void testListarClientesActivos() {
        Usuario usuario2 = new Usuario("María", "García", "maria@example.com", "0987654321", "password", Rol.CLIENTE);
        usuario2.setId("124");
        usuario2.setActivo(true);
        
        when(usuarioRepository.findByRolAndActivo(Rol.CLIENTE, true)).thenReturn(Arrays.asList(usuarioTest, usuario2));

        List<UsuarioDetalleDTO> resultado = usuarioService.listarClientesActivos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(usuarioRepository, times(1)).findByRolAndActivo(Rol.CLIENTE, true);
    }

    // ============ Tests de Obtención de Detalles ============

    @Test
    void testObtenerDetallesUsuario() {
        when(usuarioRepository.findById("123")).thenReturn(Optional.of(usuarioTest));

        UsuarioDetalleDTO resultado = usuarioService.obtenerDetallesUsuario("123");

        assertNotNull(resultado);
        assertEquals("123", resultado.getId());
        assertEquals("Juan", resultado.getNombre());
        assertEquals(5, resultado.getTotalCompras());
        assertEquals(150000.0, resultado.getMontoTotalCompras());
    }

    @Test
    void testObtenerDetallesUsuarioNoEncontrado() {
        when(usuarioRepository.findById("999")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> usuarioService.obtenerDetallesUsuario("999"));
    }

    // ============ Tests de Actualización por Admin ============

    @Test
    void testActualizarUsuarioAdminExitoso() {
        ActualizarUsuarioAdminDTO usuarioDTO = new ActualizarUsuarioAdminDTO(
            "Pedro", "Sánchez", "pedro@example.com", "1111111111", 
            "Calle 10", "Barranquilla", "Atlántico", "080001", true
        );
        
        when(usuarioRepository.findById("123")).thenReturn(Optional.of(usuarioTest));
        when(usuarioRepository.existsByEmail("pedro@example.com")).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioTest);

        Usuario resultado = usuarioService.actualizarUsuarioAdmin("123", usuarioDTO);

        assertNotNull(resultado);
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void testActualizarUsuarioAdminEmailDuplicado() {
        ActualizarUsuarioAdminDTO usuarioDTO = new ActualizarUsuarioAdminDTO(
            "Pedro", "Sánchez", "otro@example.com", "1111111111", 
            "Calle 10", "Barranquilla", "Atlántico", "080001", true
        );
        
        when(usuarioRepository.findById("123")).thenReturn(Optional.of(usuarioTest));
        when(usuarioRepository.existsByEmail("otro@example.com")).thenReturn(true);

        assertThrows(RuntimeException.class, () -> usuarioService.actualizarUsuarioAdmin("123", usuarioDTO));
    }

    // ============ Tests de Activación/Desactivación ============

    @Test
    void testDesactivarUsuario() {
        when(usuarioRepository.findById("123")).thenReturn(Optional.of(usuarioTest));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioTest);

        usuarioService.desactivarUsuario("123");

        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void testActivarUsuario() {
        when(usuarioRepository.findById("123")).thenReturn(Optional.of(usuarioTest));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioTest);

        usuarioService.activarUsuario("123");

        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    // ============ Tests de Búsqueda ============

    @Test
    void testBuscarUsuarios() {
        Usuario usuario2 = new Usuario("Juan Carlos", "López", "juancarlos@example.com", "0987654321", "password", Rol.CLIENTE);
        usuario2.setId("125");
        
        when(usuarioRepository.buscarPorNombreOApellido("Juan")).thenReturn(Arrays.asList(usuarioTest, usuario2));

        List<UsuarioDetalleDTO> resultado = usuarioService.buscarUsuarios("Juan");

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(usuarioRepository, times(1)).buscarPorNombreOApellido("Juan");
    }

    @Test
    void testBuscarUsuariosVacio() {
        when(usuarioRepository.buscarPorNombreOApellido("NoExiste")).thenReturn(Arrays.asList());

        List<UsuarioDetalleDTO> resultado = usuarioService.buscarUsuarios("NoExiste");

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }
}