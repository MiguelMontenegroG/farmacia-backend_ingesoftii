package com.farmacia;

import com.farmacia.model.Comentario;
import com.farmacia.repository.ComentarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@DisplayName("Pruebas de Repositorio de Comentarios")
class ComentarioRepoTest {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Test
    @DisplayName("Crear múltiples comentarios")
    void testCrearMultiplesComentarios() {
        // Arrange - Crear 3 comentarios
        Comentario c1 = new Comentario("producto1", "usuario1", "Juan", 5, "Excelente");
        c1.setTitulo("Muy bueno");
        c1.setContenido("Realmente funciona muy bien para el dolor");

        Comentario c2 = new Comentario("producto1", "usuario2", "María", 4, "Bueno");
        c2.setTitulo("Muy recomendado");
        c2.setContenido("Funciona bien aunque es un poco caro");

        Comentario c3 = new Comentario("producto2", "usuario1", "Juan", 3, "Regular");
        c3.setTitulo("Satisfactorio");
        c3.setContenido("Cumple su función pero podría mejorar");

        // Act
        comentarioRepository.saveAll(List.of(c1, c2, c3));

        // Assert
        List<Comentario> todos = comentarioRepository.findAll();
        assertThat(todos).hasSize(3);
    }

    @Test
    @DisplayName("Obtener comentarios activos de un producto")
    void testObtenerComentariosProducto() {
        // Arrange
        Comentario c1 = new Comentario("producto1", "usuario1", "Juan", 5, "Excelente");
        c1.setTitulo("Perfecto");
        c1.setContenido("Funciona perfectamente para mi necesidad");
        c1.setFechaCreacion(LocalDateTime.now());

        Comentario c2 = new Comentario("producto1", "usuario2", "María", 4, "Bueno");
        c2.setTitulo("Recomendado");
        c2.setContenido("Muy buen producto");
        c2.setFechaCreacion(LocalDateTime.now());

        Comentario c3 = new Comentario("producto1", "usuario3", "Pedro", 2, "Malo");
        c3.setTitulo("No me gustó");
        c3.setContenido("No cumple con lo que promete");
        c3.setActivo(false); // Desactivado
        c3.setFechaCreacion(LocalDateTime.now());

        comentarioRepository.saveAll(List.of(c1, c2, c3));

        // Act
        List<Comentario> activos = comentarioRepository.findByProductoIdAndActivoTrue("producto1");

        // Assert
        assertThat(activos).hasSize(2);
        assertThat(activos).allMatch(c -> c.isActivo());
    }

    @Test
    @DisplayName("Obtener comentarios de un usuario")
    void testObtenerComentariosUsuario() {
        // Arrange
        Comentario c1 = new Comentario("producto1", "usuario1", "Juan", 5, "Excelente");
        c1.setTitulo("Perfecto");
        c1.setContenido("Funciona perfectamente");

        Comentario c2 = new Comentario("producto2", "usuario1", "Juan", 4, "Bueno");
        c2.setTitulo("Recomendado");
        c2.setContenido("Muy buen producto");

        Comentario c3 = new Comentario("producto3", "usuario2", "María", 5, "Excelente");
        c3.setTitulo("Perfecto");
        c3.setContenido("Muy satisfecha");

        comentarioRepository.saveAll(List.of(c1, c2, c3));

        // Act
        List<Comentario> usuarioComentarios = comentarioRepository.findByUsuarioId("usuario1");

        // Assert
        assertThat(usuarioComentarios).hasSize(2);
        assertThat(usuarioComentarios).allMatch(c -> c.getUsuarioId().equals("usuario1"));
    }

    @Test
    @DisplayName("Verificar si usuario ya comentó un producto")
    void testVerificarComentarioDuplicado() {
        // Arrange
        Comentario c1 = new Comentario("producto1", "usuario1", "Juan", 5, "Excelente");
        c1.setTitulo("Perfecto");
        c1.setContenido("Funciona perfectamente");
        comentarioRepository.save(c1);

        // Act
        Optional<Comentario> existe = comentarioRepository
            .findByProductoIdAndUsuarioId("producto1", "usuario1");

        // Assert
        assertThat(existe).isPresent();
        assertThat(existe.get().getNombreUsuario()).isEqualTo("Juan");
    }

    @Test
    @DisplayName("Obtener comentarios flagged como spam")
    void testObtenerComentariosSpam() {
        // Arrange
        Comentario c1 = new Comentario("producto1", "usuario1", "Juan", 1, "Malo");
        c1.setTitulo("Spam");
        c1.setContenido("Contenido sospechoso");
        c1.setFlaggedAsSpam(true);

        Comentario c2 = new Comentario("producto1", "usuario2", "María", 5, "Excelente");
        c2.setTitulo("Perfecto");
        c2.setContenido("Funciona muy bien");

        comentarioRepository.saveAll(List.of(c1, c2));

        // Act
        List<Comentario> spam = comentarioRepository.findByFlaggedAsSpamTrue();

        // Assert
        assertThat(spam).hasSize(1);
        assertThat(spam.get(0).isFlaggedAsSpam()).isTrue();
    }

    @Test
    @DisplayName("Contar comentarios de un producto")
    void testContarComentariosProducto() {
        // Arrange
        Comentario c1 = new Comentario("producto1", "usuario1", "Juan", 5, "Excelente");
        Comentario c2 = new Comentario("producto1", "usuario2", "María", 4, "Bueno");
        Comentario c3 = new Comentario("producto2", "usuario1", "Juan", 3, "Regular");

        comentarioRepository.saveAll(List.of(c1, c2, c3));

        // Act
        long cantidad = comentarioRepository.countByProductoIdAndActivoTrue("producto1");

        // Assert
        assertThat(cantidad).isEqualTo(2);
    }

    @Test
    @DisplayName("Contar comentarios de un usuario")
    void testContarComentariosUsuario() {
        // Arrange
        Comentario c1 = new Comentario("producto1", "usuario1", "Juan", 5, "Excelente");
        Comentario c2 = new Comentario("producto2", "usuario1", "Juan", 4, "Bueno");
        Comentario c3 = new Comentario("producto3", "usuario2", "María", 5, "Excelente");

        comentarioRepository.saveAll(List.of(c1, c2, c3));

        // Act
        long cantidad = comentarioRepository.countByUsuarioId("usuario1");

        // Assert
        assertThat(cantidad).isEqualTo(2);
    }

    @Test
    @DisplayName("Obtener comentarios con altos reportes de spam")
    void testObtenerComentariosAltoSpam() {
        // Arrange
        Comentario c1 = new Comentario("producto1", "usuario1", "Juan", 1, "Spam");
        c1.setReportesSpam(5);

        Comentario c2 = new Comentario("producto1", "usuario2", "María", 4, "Bueno");
        c2.setReportesSpam(1);

        comentarioRepository.saveAll(List.of(c1, c2));

        // Act
        List<Comentario> spamAlto = comentarioRepository.findComentariosWithHighSpamReports(3);

        // Assert
        assertThat(spamAlto).hasSize(1);
        assertThat(spamAlto.get(0).getReportesSpam()).isGreaterThanOrEqualTo(3);
    }
}