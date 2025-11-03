package com.farmacia.controller;

import com.farmacia.model.Favorito;
import com.farmacia.service.FavoritoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favoritos")
@Tag(name = "Favoritos", description = "API para la gestión de productos favoritos de los usuarios")
@CrossOrigin(origins = "http://localhost:3000")
public class FavoritoController {

    @Autowired
    private FavoritoService favoritoService;


    @Operation(summary = "Obtener lista de favoritos por usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Favoritos obtenidos correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{usuarioId}")
    public ResponseEntity<Favorito> obtenerFavoritosPorUsuario(
            @Parameter(description = "ID del usuario", required = true)
            @PathVariable String usuarioId) {
        try {
            Favorito favorito = favoritoService.obtenerFavoritosPorUsuario(usuarioId);
            return ResponseEntity.ok(favorito);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    @Operation(summary = "Agregar producto a favoritos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto agregado a favoritos"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })

    @PostMapping("/{usuarioId}/agregar/{productoId}")
    public ResponseEntity<Favorito> agregarProductoAFavoritos(
            @Parameter(description = "ID del usuario", required = true)
            @PathVariable String usuarioId,
            @Parameter(description = "ID del producto", required = true)
            @PathVariable String productoId) {
        try {
            Favorito favorito = favoritoService.agregarProductoAFavoritos(usuarioId, productoId);
            return ResponseEntity.ok(favorito);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    @Operation(summary = "Eliminar producto de favoritos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto eliminado de favoritos"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/{usuarioId}/eliminar/{productoId}")
    public ResponseEntity<Favorito> eliminarProductoDeFavoritos(
            @Parameter(description = "ID del usuario", required = true)
            @PathVariable String usuarioId,
            @Parameter(description = "ID del producto", required = true)
            @PathVariable String productoId) {
        try {
            Favorito favorito = favoritoService.eliminarProductoDeFavoritos(usuarioId, productoId);
            return ResponseEntity.ok(favorito);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    @Operation(summary = "Verificar si un producto está en favoritos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Resultado de la verificación obtenido correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{usuarioId}/verificar/{productoId}")
    public ResponseEntity<Boolean> esFavorito(
            @Parameter(description = "ID del usuario", required = true)
            @PathVariable String usuarioId,
            @Parameter(description = "ID del producto", required = true)
            @PathVariable String productoId) {
        try {
            boolean esFavorito = favoritoService.esFavorito(usuarioId, productoId);
            return ResponseEntity.ok(esFavorito);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    @Operation(summary = "Eliminar todos los favoritos del usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Favoritos eliminados correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })


    @DeleteMapping("/{usuarioId}/eliminar-todos")
    public ResponseEntity<Void> eliminarTodosFavoritos(
            @Parameter(description = "ID del usuario", required = true)
            @PathVariable String usuarioId) {
        try {
            favoritoService.eliminarTodosFavoritos(usuarioId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Listar solo los IDs de productos favoritos por usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "IDs obtenidos correctamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/{usuarioId}/ids")
    public ResponseEntity<List<String>> listarIdsFavoritos(
            @Parameter(description = "ID del usuario", required = true)
            @PathVariable String usuarioId) {
        try {
            List<String> ids = favoritoService.listarIdsFavoritos(usuarioId);
            return ResponseEntity.ok(ids);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
};