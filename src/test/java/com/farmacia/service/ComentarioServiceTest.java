package com.farmacia.service;

import com.farmacia.dto.ComentarioDTO;
import com.farmacia.model.Comentario;
import com.farmacia.model.Producto;
import com.farmacia.model.Usuario;
import com.farmacia.repository.ComentarioRepository;
import com.farmacia.repository.ProductoRepository;
import com.farmacia.repository.UsuarioRepository;
import com.farmacia.service.impl.ComentarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DisplayName("Pruebas de Servicio de Comentarios")
class ComentarioServiceTest {

    @Mock
    private ComentarioRepository comentarioRepository;

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private ComentarioServiceImpl comentarioService;

    private ComentarioDTO comentarioDTO;
    private Comentario comentario;
    private Producto producto;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configurar datos de prueba
        usuario = new Usuario();
        usuario.setId("usuario1");
        usuario.setNombre("Juan");

        producto = new Producto("Paracetamol", "Analgésico", new BigDecimal("2500"), null);
        producto.setId("producto1");

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
    @DisplayName("Crear comentario exitosamente")
    void testCrearComentarioExitosamente() {
        // Arrange
        when(productoRepository.existsById("producto1")).thenReturn(true);
        when(usuarioRepository.existsById("usuario1")).thenReturn(true);
        when(comentarioRepository.findByProductoIdAndUsuarioId("producto1", "usuario1"))
            .thenReturn(Optional.empty());
        when(comentarioRepository.save(any(Comentario.class))).thenReturn(comentario);

        // Act
        Comentario resultado = comentarioService.crearComentario(comentarioDTO);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo("comentario1");
        assertThat(resultado.getCalificacion()).isEqualTo(5);
        assertThat(resultado.isActivo()).isTrue();
        verify(comentarioRepository, times(1)).save(any(Comentario.class));
    }

    @Test
    @DisplayName("Rechazar comentario si producto no existe")
    void testCrearComentarioProductoNoExiste() {
        // Arrange
        when(productoRepository.existsById("producto1")).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> comentarioService.crearComentario(comentarioDTO))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("El producto no existe");
    }

    @Test
    @DisplayName("Rechazar comentario si usuario no existe")
    void testCrearComentarioUsuarioNoExiste() {
        // Arrange
        when(productoRepository.existsById("producto1")).thenReturn(true);
        when(usuarioRepository.existsById("usuario1")).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> comentarioService.crearComentario(comentarioDTO))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("El usuario no existe");
    }

    @Test
    @DisplayName("Rechazar si usuario ya comentó el producto")
    void testCrearComentarioDuplicado() {
        // Arrange
        when(productoRepository.existsById("producto1")).thenReturn(true);
        when(usuarioRepository.existsById("usuario1")).thenReturn(true);
        when(comentarioRepository.findByProductoIdAndUsuarioId("producto1", "usuario1"))
            .thenReturn(Optional.of(comentario));

        // Act & Assert
        assertThatThrownBy(() -> comentarioService.crearComentario(comentarioDTO))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Ya has comentado este producto");
    }

    @Test
    @DisplayName("Rechazar comentario con palabras prohibidas")
    void testValidacionSpamPalabrasProhibidas() {
        // Arrange
        comentarioDTO.setContenido("Compra aqui por whatsapp y gana crypto");
        when(productoRepository.existsById("producto1")).thenReturn(true);
        when(usuarioRepository.existsById("usuario1")).thenReturn(true);
        when(comentarioRepository.findByProductoIdAndUsuarioId("producto1", "usuario1"))
            .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> comentarioService.crearComentario(comentarioDTO))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("El comentario contiene contenido sospechoso o palabras prohibidas");
    }

    @Test
    @DisplayName("Rechazar comentario completamente en mayúsculas")
    void testValidacionSpamMayusculas() {
        // Arrange
        comentarioDTO.setContenido("PRODUCTO EXCELENTE COMPRA YA MISMO");
        when(productoRepository.existsById("producto1")).thenReturn(true);
        when(usuarioRepository.existsById("usuario1")).thenReturn(true);
        when(comentarioRepository.findByProductoIdAndUsuarioId("producto1", "usuario1"))
            .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> comentarioService.crearComentario(comentarioDTO))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("El comentario no puede estar completamente en mayúsculas");
    }

    @Test
    @DisplayName("Rechazar comentario con URLs")
    void testValidacionSpamURLs() {
        // Arrange
        comentarioDTO.setContenido("Visita https://malicious.com para más info");
        when(productoRepository.existsById("producto1")).thenReturn(true);
        when(usuarioRepository.existsById("usuario1")).thenReturn(true);
        when(comentarioRepository.findByProductoIdAndUsuarioId("producto1", "usuario1"))
            .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> comentarioService.crearComentario(comentarioDTO))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("No se permiten URLs en comentarios");
    }

    @Test
    @DisplayName("Obtener comentarios de un producto")
    void testObtenerComentariosProducto() {
        // Arrange
        List<Comentario> comentarios = List.of(comentario);
        when(comentarioRepository.findByProductoIdAndActivoTrue("producto1"))
            .thenReturn(comentarios);

        // Act
        List<ComentarioDTO> resultado = comentarioService.obtenerComentariosProducto("producto1");

        // Assert
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getId()).isEqualTo("comentario1");
    }

    @Test
    @DisplayName("Obtener comentarios de un usuario")
    void testObtenerComentariosUsuario() {
        // Arrange
        List<Comentario> comentarios = List.of(comentario);
        when(comentarioRepository.findByUsuarioId("usuario1"))
            .thenReturn(comentarios);

        // Act
        List<ComentarioDTO> resultado = comentarioService.obtenerComentariosUsuario("usuario1");

        // Assert
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNombreUsuario()).isEqualTo("Juan");
    }

    @Test
    @DisplayName("Obtener comentario específico")
    void testObtenerComentario() {
        // Arrange
        when(comentarioRepository.findById("comentario1"))
            .thenReturn(Optional.of(comentario));

        // Act
        var resultado = comentarioService.obtenerComentario("comentario1");

        // Assert
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getCalificacion()).isEqualTo(5);
    }

    @Test
    @DisplayName("Actualizar comentario existente")
    void testActualizarComentario() {
        // Arrange
        ComentarioDTO actualizadoDTO = new ComentarioDTO();
        actualizadoDTO.setUsuarioId("usuario1");
        actualizadoDTO.setTitulo("Muy bueno");
        actualizadoDTO.setContenido("Realmente excelente para el dolor de cabeza");

        when(comentarioRepository.findById("comentario1"))
            .thenReturn(Optional.of(comentario));
        when(comentarioRepository.save(any(Comentario.class)))
            .thenReturn(comentario);

        // Act
        Comentario resultado = comentarioService.actualizarComentario("comentario1", actualizadoDTO);

        // Assert
        assertThat(resultado).isNotNull();
        verify(comentarioRepository, times(1)).save(any(Comentario.class));
    }

    @Test
    @DisplayName("Rechazar actualización si no es el propietario")
    void testActualizarComentarioNoAutorizado() {
        // Arrange
        ComentarioDTO actualizadoDTO = new ComentarioDTO();
        actualizadoDTO.setUsuarioId("usuario2");

        when(comentarioRepository.findById("comentario1"))
            .thenReturn(Optional.of(comentario));

        // Act & Assert
        assertThatThrownBy(() -> comentarioService.actualizarComentario("comentario1", actualizadoDTO))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("No tienes permiso para editar este comentario");
    }

    @Test
    @DisplayName("Eliminar (desactivar) comentario")
    void testEliminarComentario() {
        // Arrange
        when(comentarioRepository.findById("comentario1"))
            .thenReturn(Optional.of(comentario));
        when(comentarioRepository.save(any(Comentario.class)))
            .thenReturn(comentario);

        // Act
        comentarioService.eliminarComentario("comentario1");

        // Assert
        verify(comentarioRepository, times(1)).save(any(Comentario.class));
    }

    @Test
    @DisplayName("Reportar comentario como spam")
    void testReportarSpam() {
        // Arrange
        when(comentarioRepository.findById("comentario1"))
            .thenReturn(Optional.of(comentario));
        when(comentarioRepository.save(any(Comentario.class)))
            .thenReturn(comentario);

        // Act
        Comentario resultado = comentarioService.reportarSpam("comentario1");

        // Assert
        assertThat(resultado).isNotNull();
        verify(comentarioRepository, times(1)).save(any(Comentario.class));
    }

    @Test
    @DisplayName("Flagear comentario cuando reportes superan límite")
    void testFlagearComentarioSpam() {
        // Arrange
        comentario.setReportesSpam(2);
        when(comentarioRepository.findById("comentario1"))
            .thenReturn(Optional.of(comentario));
        when(comentarioRepository.save(any(Comentario.class)))
            .thenReturn(comentario);

        // Act
        comentarioService.reportarSpam("comentario1");

        // Assert
        verify(comentarioRepository, times(1)).save(any(Comentario.class));
    }

    @Test
    @DisplayName("Obtener comentarios flagged como spam")
    void testObtenerComentariosSpam() {
        // Arrange
        comentario.setFlaggedAsSpam(true);
        List<Comentario> comentariosSpam = List.of(comentario);
        when(comentarioRepository.findByFlaggedAsSpamTrue())
            .thenReturn(comentariosSpam);

        // Act
        List<ComentarioDTO> resultado = comentarioService.obtenerComentariosSpam();

        // Assert
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).isFlaggedAsSpam()).isTrue();
    }

    @Test
    @DisplayName("Aceptar comentario (moderación)")
    void testAceptarComentario() {
        // Arrange
        comentario.setFlaggedAsSpam(true);
        when(comentarioRepository.findById("comentario1"))
            .thenReturn(Optional.of(comentario));
        when(comentarioRepository.save(any(Comentario.class)))
            .thenReturn(comentario);

        // Act
        comentarioService.aceptarComentario("comentario1");

        // Assert
        verify(comentarioRepository, times(1)).save(any(Comentario.class));
    }

    @Test
    @DisplayName("Rechazar comentario (moderación)")
    void testRechazarComentario() {
        // Arrange
        when(comentarioRepository.findById("comentario1"))
            .thenReturn(Optional.of(comentario));
        when(comentarioRepository.save(any(Comentario.class)))
            .thenReturn(comentario);

        // Act
        comentarioService.rechazarComentario("comentario1");

        // Assert
        verify(comentarioRepository, times(1)).save(any(Comentario.class));
    }

    @Test
    @DisplayName("Obtener calificación promedio de producto")
    void testObtenerCalificacionPromedio() {
        // Arrange
        Comentario c1 = new Comentario();
        c1.setCalificacion(5);
        Comentario c2 = new Comentario();
        c2.setCalificacion(4);
        Comentario c3 = new Comentario();
        c3.setCalificacion(5);

        List<Comentario> comentarios = List.of(c1, c2, c3);
        when(comentarioRepository.findByProductoIdAndActivoTrue("producto1"))
            .thenReturn(comentarios);

        // Act
        Double promedio = comentarioService.obtenerCalificacionPromedio("producto1");

        // Assert
        assertThat(promedio).isEqualTo(4.67, within(0.01));
    }

    @Test
    @DisplayName("Calificación promedio cuando no hay comentarios")
    void testCalificacionPromediaVacia() {
        // Arrange
        when(comentarioRepository.findByProductoIdAndActivoTrue("producto1"))
            .thenReturn(new ArrayList<>());

        // Act
        Double promedio = comentarioService.obtenerCalificacionPromedio("producto1");

        // Assert
        assertThat(promedio).isEqualTo(0.0);
    }
}