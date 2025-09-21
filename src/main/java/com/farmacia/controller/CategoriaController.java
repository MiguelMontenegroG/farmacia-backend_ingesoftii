package com.farmacia.controller;

import com.farmacia.dto.CategoriaDTO;
import com.farmacia.model.Categoria;
import com.farmacia.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categorias")
@Tag(name = "Categorías", description = "API para gestión de categorías de productos")
@CrossOrigin(origins = "*")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Operation(summary = "Obtener todas las categorías activas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categorías obtenidas exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> obtenerTodasLasCategorias() {
        try {
            List<Categoria> categorias = categoriaService.obtenerTodasLasCategorias();
            List<CategoriaDTO> categoriasDTO = categorias.stream()
                    .map(this::convertirADTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(categoriasDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Obtener categorías raíz")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categorías raíz obtenidas exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/raiz")
    public ResponseEntity<List<CategoriaDTO>> obtenerCategoriasRaiz() {
        try {
            List<Categoria> categorias = categoriaService.obtenerCategoriasRaiz();
            List<CategoriaDTO> categoriasDTO = categorias.stream()
                    .map(this::convertirADTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(categoriasDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Obtener subcategorías por categoría padre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subcategorías obtenidas exitosamente"),
            @ApiResponse(responseCode = "400", description = "ID de categoría padre inválido"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/subcategorias/{categoriaPadreId}")
    public ResponseEntity<List<CategoriaDTO>> obtenerSubcategorias(
            @Parameter(description = "ID de la categoría padre", required = true)
            @PathVariable String categoriaPadreId) {

        if (categoriaPadreId == null || categoriaPadreId.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            List<Categoria> categorias = categoriaService.obtenerSubcategorias(categoriaPadreId);
            List<CategoriaDTO> categoriasDTO = categorias.stream()
                    .map(this::convertirADTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(categoriasDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Buscar categorías por nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Parámetro de búsqueda inválido"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/buscar")
    public ResponseEntity<List<CategoriaDTO>> buscarCategorias(
            @Parameter(description = "Término de búsqueda", required = true)
            @RequestParam String nombre) {

        if (nombre == null || nombre.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            List<Categoria> categorias = categoriaService.buscarCategoriasPorNombre(nombre);
            List<CategoriaDTO> categoriasDTO = categorias.stream()
                    .map(this::convertirADTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(categoriasDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Método auxiliar para convertir Categoria a CategoriaDTO
    private CategoriaDTO convertirADTO(Categoria categoria) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(categoria.getId());
        dto.setNombre(categoria.getNombre());
        dto.setDescripcion(categoria.getDescripcion());
        dto.setImagenUrl(categoria.getImagenUrl());
        dto.setKeywords(categoria.getKeywords());
        dto.setOrden(categoria.getOrden());
        dto.setEsCategoriaRaiz(categoria.esCategoriaRaiz());

        if (categoria.getCategoriaPadre() != null) {
            dto.setCategoriaPadreId(categoria.getCategoriaPadre().getId());
            dto.setCategoriaPadreNombre(categoria.getCategoriaPadre().getNombre());
        }

        return dto;
    }
}