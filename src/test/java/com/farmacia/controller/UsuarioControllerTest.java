package com.farmacia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.farmacia.Enum.Rol;
import com.farmacia.dto.ActualizarPerfilDTO;
import com.farmacia.dto.ActualizarDireccionDTO;
import com.farmacia.dto.ActualizarUsuarioAdminDTO;
import com.farmacia.dto.UsuarioDetalleDTO;
import com.farmacia.model.Usuario;
import com.farmacia.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    private Usuario usuarioTest;
    private UsuarioDetalleDTO usuarioDetalleTest;

    @BeforeEach
    void setUp() {
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

        usuarioDetalleTest = new UsuarioDetalleDTO(
            "123", "Juan", "Pérez", "juan@example.com", "1234567890",
            "Calle 1 #2-3", "Bogotá", "Cundinamarca", "110111",
            Rol.CLIENTE, true, LocalDateTime.now(), LocalDateTime.now(),
            5, 150000.0, null
        );
    }

    // ============ Tests de Gestión de Perfil (Cliente) ============

    @Test
    void testObtenerPerfilUsuario() throws Exception {
        when(usuarioService.obtenerUsuarioPorId("123")).thenReturn(usuarioTest);

        mockMvc.perform(get("/api/usuarios/123/perfil"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.nombre").value("Juan"))
                .andExpect(jsonPath("$.data.apellido").value("Pérez"));

        verify(usuarioService, times(1)).obtenerUsuarioPorId("123");
    }

    @Test
    void testObtenerPerfilUsuarioNoEncontrado() throws Exception {
        when(usuarioService.obtenerUsuarioPorId("999")).thenThrow(new RuntimeException("Usuario no encontrado"));

        mockMvc.perform(get("/api/usuarios/999/perfil"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testActualizarPerfilUsuario() throws Exception {
        ActualizarPerfilDTO perfilDTO = new ActualizarPerfilDTO("Carlos", "López", "3156789012");

        when(usuarioService.actualizarPerfil(eq("123"), any(ActualizarPerfilDTO.class)))
                .thenReturn(usuarioTest);

        mockMvc.perform(put("/api/usuarios/123/perfil")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(perfilDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").containsString("Perfil actualizado"));

        verify(usuarioService, times(1)).actualizarPerfil(eq("123"), any(ActualizarPerfilDTO.class));
    }

    @Test
    void testActualizarPerfilValidacionFallida() throws Exception {
        // Nombre muy corto
        ActualizarPerfilDTO perfilDTO = new ActualizarPerfilDTO("C", "López", "3156789012");

        mockMvc.perform(put("/api/usuarios/123/perfil")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(perfilDTO)))
                .andExpect(status().isBadRequest());
    }

    // ============ Tests de Gestión de Dirección ============

    @Test
    void testActualizarDireccion() throws Exception {
        ActualizarDireccionDTO direccionDTO = new ActualizarDireccionDTO(
            "Calle 5 #10-15", "Medellín", "Antioquia", "050001"
        );

        when(usuarioService.actualizarDireccion(eq("123"), any(ActualizarDireccionDTO.class)))
                .thenReturn(usuarioTest);

        mockMvc.perform(put("/api/usuarios/123/direccion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(direccionDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").containsString("Dirección actualizada"));

        verify(usuarioService, times(1)).actualizarDireccion(eq("123"), any(ActualizarDireccionDTO.class));
    }

    @Test
    void testActualizarDireccionCodigoPostalInvalido() throws Exception {
        // Código postal inválido (solo 3 dígitos)
        ActualizarDireccionDTO direccionDTO = new ActualizarDireccionDTO(
            "Calle 5 #10-15", "Medellín", "Antioquia", "050"
        );

        mockMvc.perform(put("/api/usuarios/123/direccion")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(direccionDTO)))
                .andExpect(status().isBadRequest());
    }

    // ============ Tests de Gestión de Usuarios (Admin) ============

    @Test
    void testListarUsuarios() throws Exception {
        List<UsuarioDetalleDTO> usuarios = Arrays.asList(usuarioDetalleTest);
        when(usuarioService.listarUsuarios()).thenReturn(usuarios);

        mockMvc.perform(get("/api/usuarios/admin/lista"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.usuarios", hasSize(1)))
                .andExpect(jsonPath("$.data.total").value(1));

        verify(usuarioService, times(1)).listarUsuarios();
    }

    @Test
    void testListarClientesActivos() throws Exception {
        List<UsuarioDetalleDTO> usuarios = Arrays.asList(usuarioDetalleTest);
        when(usuarioService.listarClientesActivos()).thenReturn(usuarios);

        mockMvc.perform(get("/api/usuarios/admin/clientes-activos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.usuarios", hasSize(1)));

        verify(usuarioService, times(1)).listarClientesActivos();
    }

    @Test
    void testBuscarUsuarios() throws Exception {
        List<UsuarioDetalleDTO> usuarios = Arrays.asList(usuarioDetalleTest);
        when(usuarioService.buscarUsuarios("Juan")).thenReturn(usuarios);

        mockMvc.perform(get("/api/usuarios/admin/buscar").param("termino", "Juan"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.usuarios", hasSize(1)));

        verify(usuarioService, times(1)).buscarUsuarios("Juan");
    }

    @Test
    void testBuscarUsuariosVacio() throws Exception {
        when(usuarioService.buscarUsuarios("NoExiste")).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/usuarios/admin/buscar").param("termino", "NoExiste"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.usuarios", hasSize(0)));
    }

    @Test
    void testObtenerDetallesUsuario() throws Exception {
        when(usuarioService.obtenerDetallesUsuario("123")).thenReturn(usuarioDetalleTest);

        mockMvc.perform(get("/api/usuarios/admin/detalles/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.nombre").value("Juan"))
                .andExpect(jsonPath("$.data.totalCompras").value(5));

        verify(usuarioService, times(1)).obtenerDetallesUsuario("123");
    }

    @Test
    void testObtenerDetallesUsuarioNoEncontrado() throws Exception {
        when(usuarioService.obtenerDetallesUsuario("999"))
                .thenThrow(new RuntimeException("Usuario no encontrado"));

        mockMvc.perform(get("/api/usuarios/admin/detalles/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testActualizarUsuarioAdmin() throws Exception {
        ActualizarUsuarioAdminDTO usuarioDTO = new ActualizarUsuarioAdminDTO(
            "Pedro", "Sánchez", "pedro@example.com", "1111111111",
            "Calle 10", "Barranquilla", "Atlántico", "080001", true
        );

        when(usuarioService.actualizarUsuarioAdmin(eq("123"), any(ActualizarUsuarioAdminDTO.class)))
                .thenReturn(usuarioTest);

        mockMvc.perform(put("/api/usuarios/admin/123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

        verify(usuarioService, times(1)).actualizarUsuarioAdmin(eq("123"), any(ActualizarUsuarioAdminDTO.class));
    }

    @Test
    void testActualizarUsuarioAdminEmailDuplicado() throws Exception {
        ActualizarUsuarioAdminDTO usuarioDTO = new ActualizarUsuarioAdminDTO(
            "Pedro", "Sánchez", "otro@example.com", "1111111111",
            "Calle 10", "Barranquilla", "Atlántico", "080001", true
        );

        when(usuarioService.actualizarUsuarioAdmin(eq("123"), any(ActualizarUsuarioAdminDTO.class)))
                .thenThrow(new RuntimeException("El email ya está registrado por otro usuario"));

        mockMvc.perform(put("/api/usuarios/admin/123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioDTO)))
                .andExpect(status().isBadRequest());
    }

    // ============ Tests de Activación/Desactivación ============

    @Test
    void testDesactivarUsuario() throws Exception {
        doNothing().when(usuarioService).desactivarUsuario("123");

        mockMvc.perform(put("/api/usuarios/admin/123/desactivar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").containsString("desactivado"));

        verify(usuarioService, times(1)).desactivarUsuario("123");
    }

    @Test
    void testActivarUsuario() throws Exception {
        doNothing().when(usuarioService).activarUsuario("123");

        mockMvc.perform(put("/api/usuarios/admin/123/activar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").containsString("activado"));

        verify(usuarioService, times(1)).activarUsuario("123");
    }

    @Test
    void testDesactivarUsuarioNoEncontrado() throws Exception {
        doThrow(new RuntimeException("Usuario no encontrado"))
                .when(usuarioService).desactivarUsuario("999");

        mockMvc.perform(put("/api/usuarios/admin/999/desactivar"))
                .andExpect(status().isNotFound());
    }

    // ============ Tests de Status y Estructura de Respuesta ============

    @Test
    void testEstructuraRespuestaExitosa() throws Exception {
        when(usuarioService.obtenerUsuarioPorId("123")).thenReturn(usuarioTest);

        mockMvc.perform(get("/api/usuarios/123/perfil"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.data").exists());
    }
}