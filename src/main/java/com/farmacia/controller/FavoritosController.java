package com.farmacia.controller;

import com.farmacia.dto.ApiResponse;
import com.farmacia.model.Favorito;
import com.farmacia.model.Usuario;
import com.farmacia.service.FavoritosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favoritos")
@Tag(name = "Favoritos", description = "API para gestión de favoritos")
public class FavoritosController {

    @Autowired
    private FavoritosService favoritosService;

    @GetMapping
    @Operation(summary = "Obtener favoritos del usuario")
    public ResponseEntity<ApiResponse<List<Favorito>>> obtenerFavoritos() {
        try {
            // En entorno académico, usamos un ID de usuario fijo para pruebas
            String usuarioId = "usuario-prueba-id";
            
            List<Favorito> favoritos = favoritosService.obtenerFavoritosPorUsuario(usuarioId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Favoritos obtenidos exitosamente", favoritos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, "Error al obtener favoritos: " + e.getMessage()));
        }
    }

    @GetMapping("/conteo")
    @Operation(summary = "Obtener conteo de favoritos")
    public ResponseEntity<ApiResponse<Long>> obtenerConteoFavoritos() {
        try {
            // En entorno académico, usamos un ID de usuario fijo para pruebas
            String usuarioId = "usuario-prueba-id";
            
            long conteo = favoritosService.contarFavoritosPorUsuario(usuarioId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Conteo obtenido exitosamente", conteo));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, "Error al obtener conteo: " + e.getMessage()));
        }
    }

    @GetMapping("/verificar/{productoId}")
    @Operation(summary = "Verificar si un producto es favorito")
    public ResponseEntity<ApiResponse<Boolean>> verificarFavorito(@PathVariable String productoId) {
        try {
            // En entorno académico, usamos un ID de usuario fijo para pruebas
            String usuarioId = "usuario-prueba-id";
            
            boolean esFavorito = favoritosService.esFavorito(usuarioId, productoId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Verificación completada", esFavorito));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, "Error al verificar favorito: " + e.getMessage()));
        }
    }

    @PostMapping("/{productoId}")
    @Operation(summary = "Agregar producto a favoritos")
    public ResponseEntity<ApiResponse<Void>> agregarFavorito(@PathVariable String productoId) {
        try {
            // En entorno académico, usamos un ID de usuario fijo para pruebas
            String usuarioId = "usuario-prueba-id";
            
            favoritosService.agregarAFavoritos(usuarioId, productoId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Producto agregado a favoritos", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, "Error al agregar a favoritos: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{productoId}")
    @Operation(summary = "Eliminar producto de favoritos")
    public ResponseEntity<ApiResponse<Void>> eliminarFavorito(@PathVariable String productoId) {
        try {
            // En entorno académico, usamos un ID de usuario fijo para pruebas
            String usuarioId = "usuario-prueba-id";
            
            favoritosService.eliminarDeFavoritos(usuarioId, productoId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Producto eliminado de favoritos", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, "Error al eliminar de favoritos: " + e.getMessage()));
        }
    }
}