package com.farmacia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.farmacia.dto.ComentarioDTO;
import com.farmacia.model.Comentario;
import com.farmacia.service.ComentarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Pruebas de Controlador de Comentarios")
class ComentarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ComentarioService comentarioService;

    private ComentarioDTO comentarioDTO;
    private Comentario comentario;

    @BeforeEach
    void setUp() {
        comentarioDTO = new ComentarioDTO(
            "producto1",
            "usuario1",
            "Juan",
            5,
            "Excelente producto",
            "Me funcionó muy bien, excelente para el dolor"
        );

        comentario = new Comentario();
        comentario.setId("comentario1");
        comentario.setProductoId("producto1");
        comentario.setUsuarioId("usuario1");
        comentario.setNombreUsuario("Juan");
        comentario.setCalificacion(5);
        comentario.setTitulo("Excelente producto");
        comentario.setContenido("Me funcionó muy bien, excelente para el dolor");
        comentario.setFechaCreacion(LocalDateTime.now());
        comentario.setActivo(true);
    }

    @Test
    @DisplayName("POST /api/comentarios - Crear comentario exitosamente")
    void testCrearComentario() throws Exception {
        // Arrange
        when(comentarioService.crearComentario(any(ComentarioDTO.class)))
            .thenReturn(comentario);

        // Act & Assert
        mockMvc.perform(post("/api/comentarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(comentarioDTO)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.mensaje").value("Comentario creado exitosamente"))
            .andExpect(jsonPath("$.data.id").value("comentario1"))
            .andExpect(jsonPath("$.data.calificacion").value(5));
    }

    @Test
    @DisplayName("POST /api/comentarios - Error al crear comentario (validación)")
    void testCrearComentarioError() throws Exception {
        // Arrange
        when(comentarioService.crearComentario(any(ComentarioDTO.class)))
            .thenThrow(new IllegalArgumentException("El producto no existe"));

        // Act & Assert
        mockMvc.perform(post("/api/comentarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(comentarioDTO)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    @DisplayName("GET /api/comentarios/producto/{productoId} - Obtener comentarios del producto")
    void testObtenerComentariosProducto() throws Exception {
        // Arrange
        List<ComentarioDTO> comentarios = List.of(
            new ComentarioDTO("producto1", "usuario1", "Juan", 5, "Perfecto", "Excelente"),
            new ComentarioDTO("producto1", "usuario2", "María", 4, "Bueno", "Recomendado")
        );
        when(comentarioService.obtenerComentariosProducto("producto1"))
            .thenReturn(comentarios);
        when(comentarioService.obtenerCalificacionPromedio("producto1"))
            .thenReturn(4.5);

        // Act & Assert
        mockMvc.perform(get("/api/comentarios/producto/producto1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.comentarios").isArray())
            .andExpect(jsonPath("$.data.total").value(2))
            .andExpect(jsonPath("$.data.calificacionPromedio").value(4.5));
    }

    @Test
    @DisplayName("GET /api/comentarios/usuario/{usuarioId} - Obtener comentarios del usuario")
    void testObtenerComentariosUsuario() throws Exception {
        // Arrange
        List<ComentarioDTO> comentarios = List.of(
            new ComentarioDTO("producto1", "usuario1", "Juan", 5, "Perfecto", "Excelente")
        );
        when(comentarioService.obtenerComentariosUsuario("usuario1"))
            .thenReturn(comentarios);

        // Act & Assert
        mockMvc.perform(get("/api/comentarios/usuario/usuario1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    @DisplayName("GET /api/comentarios/{id} - Obtener comentario específico")
    void testObtenerComentario() throws Exception {
        // Arrange
        ComentarioDTO dto = new ComentarioDTO();
        dto.setId("comentario1");
        when(comentarioService.obtenerComentario("comentario1"))
            .thenReturn(Optional.of(dto));

        // Act & Assert
        mockMvc.perform(get("/api/comentarios/comentario1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @DisplayName("GET /api/comentarios/{id} - Comentario no encontrado")
    void testObtenerComentarioNoEncontrado() throws Exception {
        // Arrange
        when(comentarioService.obtenerComentario("comentario1"))
            .thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/comentarios/comentario1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /api/comentarios/{id} - Actualizar comentario")
    void testActualizarComentario() throws Exception {
        // Arrange
        comentarioDTO.setTitulo("Actualizado");
        when(comentarioService.actualizarComentario(anyString(), any(ComentarioDTO.class)))
            .thenReturn(comentario);

        // Act & Assert
        mockMvc.perform(put("/api/comentarios/comentario1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(comentarioDTO)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.mensaje").value("Comentario actualizado exitosamente"));
    }

    @Test
    @DisplayName("DELETE /api/comentarios/{id} - Eliminar comentario")
    void testEliminarComentario() throws Exception {
        // Arrange
        doNothing().when(comentarioService).eliminarComentario("comentario1");

        // Act & Assert
        mockMvc.perform(delete("/api/comentarios/comentario1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.mensaje").value("Comentario eliminado exitosamente"));
    }

    @Test
    @DisplayName("POST /api/comentarios/{id}/reportar-spam - Reportar spam")
    void testReportarSpam() throws Exception {
        // Arrange
        comentario.setReportesSpam(1);
        when(comentarioService.reportarSpam("comentario1"))
            .thenReturn(comentario);

        // Act & Assert
        mockMvc.perform(post("/api/comentarios/comentario1/reportar-spam")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.mensaje").value("Comentario reportado como spam"));
    }

    @Test
    @DisplayName("GET /api/comentarios/moderacion/spam - Obtener comentarios spam")
    void testObtenerComentariosSpam() throws Exception {
        // Arrange
        List<ComentarioDTO> spamComentarios = List.of(
            new ComentarioDTO("producto1", "usuario1", "Spammer", 1, "Spam", "Contenido spam")
        );
        when(comentarioService.obtenerComentariosSpam())
            .thenReturn(spamComentarios);

        // Act & Assert
        mockMvc.perform(get("/api/comentarios/moderacion/spam")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.total").value(1));
    }

    @Test
    @DisplayName("POST /api/comentarios/{id}/aceptar - Aceptar comentario")
    void testAceptarComentario() throws Exception {
        // Arrange
        doNothing().when(comentarioService).aceptarComentario("comentario1");

        // Act & Assert
        mockMvc.perform(post("/api/comentarios/comentario1/aceptar")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.mensaje").value("Comentario aceptado"));
    }

    @Test
    @DisplayName("POST /api/comentarios/{id}/rechazar - Rechazar comentario")
    void testRechazarComentario() throws Exception {
        // Arrange
        doNothing().when(comentarioService).rechazarComentario("comentario1");

        // Act & Assert
        mockMvc.perform(post("/api/comentarios/comentario1/rechazar")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.mensaje").value("Comentario rechazado"));
    }

    @Test
    @DisplayName("GET /api/comentarios/producto/{productoId}/calificacion-promedio - Obtener promedio")
    void testObtenerCalificacionPromedio() throws Exception {
        // Arrange
        when(comentarioService.obtenerCalificacionPromedio("producto1"))
            .thenReturn(4.5);

        // Act & Assert
        mockMvc.perform(get("/api/comentarios/producto/producto1/calificacion-promedio")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.calificacionPromedio").value(4.5));
    }
}