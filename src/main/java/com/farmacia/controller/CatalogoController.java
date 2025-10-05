package com.farmacia.controller;

import com.farmacia.dto.ProductoDTO;
import com.farmacia.model.Producto;
import com.farmacia.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/catalogo")
@Tag(name = "Catálogo", description = "API para gestión del catálogo de productos de la farmacia")
@CrossOrigin(origins = "*")
public class CatalogoController {

    @Autowired
    private ProductoService productoService;

    @Operation(summary = "Obtener catálogo completo de productos activos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Catálogo obtenido exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoDTO.class))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping
    public ResponseEntity<List<ProductoDTO>> obtenerCatalogo() {
        try {
            List<Producto> productos = productoService.obtenerCatalogo();
            List<ProductoDTO> productosDTO = productos.stream()
                    .map(this::convertirADTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(productosDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Buscar productos por nombre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Parámetro de búsqueda inválido"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/buscar")
    public ResponseEntity<List<ProductoDTO>> buscarProductos(
            @Parameter(description = "Término de búsqueda", required = true)
            @RequestParam String nombre) {

        if (nombre == null || nombre.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            List<Producto> productos = productoService.buscarProductosPorNombre(nombre);
            List<ProductoDTO> productosDTO = productos.stream()
                    .map(this::convertirADTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(productosDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Buscar productos por principio activo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Búsqueda realizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Parámetro de búsqueda inválido"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/buscar/principio-activo")
    public ResponseEntity<List<ProductoDTO>> buscarPorPrincipioActivo(
            @Parameter(description = "Principio activo a buscar", required = true)
            @RequestParam String principioActivo) {

        if (principioActivo == null || principioActivo.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            List<Producto> productos = productoService.buscarPorPrincipioActivo(principioActivo);
            List<ProductoDTO> productosDTO = productos.stream()
                    .map(this::convertirADTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(productosDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Obtener productos en oferta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Productos en oferta obtenidos exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/ofertas")
    public ResponseEntity<List<ProductoDTO>> obtenerOfertas() {
        try {
            List<Producto> productos = productoService.obtenerProductosEnOferta();
            List<ProductoDTO> productosDTO = productos.stream()
                    .map(this::convertirADTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(productosDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Obtener productos por categoría")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Productos de la categoría obtenidos exitosamente"),
            @ApiResponse(responseCode = "400", description = "ID de categoría inválido"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<ProductoDTO>> obtenerProductosPorCategoria(
            @Parameter(description = "ID de la categoría", required = true)
            @PathVariable String categoriaId) {

        if (categoriaId == null || categoriaId.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            List<Producto> productos = productoService.buscarProductosPorCategoria(categoriaId);
            List<ProductoDTO> productosDTO = productos.stream()
                    .map(this::convertirADTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(productosDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }


    private ProductoDTO convertirADTO(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        dto.setPrecioOferta(producto.getPrecioOferta());
        dto.setEnOferta(producto.isEnOferta());
        dto.setCategoriaId(producto.getCategoria() != null ? producto.getCategoria().getId() : null);
        dto.setCategoriaNombre(producto.getCategoria() != null ? producto.getCategoria().getNombre() : null);
        dto.setImagenUrl(producto.getImagenUrl());
        dto.setStock(producto.getStock());
        dto.setLaboratorio(producto.getLaboratorio());
        dto.setPrincipioActivo(producto.getPrincipioActivo());
        dto.setCodigoBarras(producto.getCodigoBarras());
        dto.setRequiereReceta(producto.isRequiereReceta());

        return dto;
    }
}