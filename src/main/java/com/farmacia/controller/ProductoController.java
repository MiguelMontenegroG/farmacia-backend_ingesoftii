package com.farmacia.controller;


import com.farmacia.dto.ProductoDTO;
import com.farmacia.model.Producto;
import com.farmacia.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> getProductos(
            @RequestParam(required = false) String busqueda,
            @RequestParam(required = false) String categoriaId,
            @RequestParam(required = false) Double precioMin,
            @RequestParam(required = false) Double precioMax,
            @RequestParam(required = false) Boolean requiereReceta,
            @RequestParam(required = false) Boolean activo,
            @RequestParam(required = false) String ordenarPor) {

        try {
            List<Producto> productos;


            if (busqueda != null && !busqueda.trim().isEmpty()) {
                productos = productoService.buscarProductosPorNombre(busqueda);
            } else if (categoriaId != null && !categoriaId.trim().isEmpty()) {
                productos = productoService.buscarProductosPorCategoria(categoriaId);
            } else {
                productos = productoService.obtenerCatalogo();
            }


            if (precioMin != null) {
                BigDecimal minPrecio = BigDecimal.valueOf(precioMin);
                productos = productos.stream()
                        .filter(p -> p.getPrecio().compareTo(minPrecio) >= 0)
                        .collect(Collectors.toList());
            }

            if (precioMax != null) {
                BigDecimal maxPrecio = BigDecimal.valueOf(precioMax);
                productos = productos.stream()
                        .filter(p -> p.getPrecio().compareTo(maxPrecio) <= 0)
                        .collect(Collectors.toList());
            }

            if (requiereReceta != null) {
                productos = productos.stream()
                        .filter(p -> p.isRequiereReceta() == requiereReceta)
                        .collect(Collectors.toList());
            }

            if (activo != null) {
                productos = productos.stream()
                        .filter(p -> p.isActivo() == activo)
                        .collect(Collectors.toList());
            }


            if (ordenarPor != null) {
                switch (ordenarPor) {
                    case "precio-asc":
                        productos.sort((a, b) -> a.getPrecio().compareTo(b.getPrecio()));
                        break;
                    case "precio-desc":
                        productos.sort((a, b) -> b.getPrecio().compareTo(a.getPrecio()));
                        break;
                    case "nombre":
                        productos.sort((a, b) -> a.getNombre().compareToIgnoreCase(b.getNombre()));
                        break;
                }
            }


            List<ProductoDTO> productosDTO = productos.stream()
                    .map(this::convertirADTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(productosDTO);

        } catch (Exception e) {
            e.printStackTrace(); // Para debug
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> getProductoPorId(@PathVariable String id) {
        try {
            Producto producto = productoService.obtenerPorId(id);
            if (producto != null) {
                return ResponseEntity.ok(convertirADTO(producto));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace(); // Para debug
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/crear")
    public ResponseEntity<ProductoDTO> crearProducto(@RequestBody Producto producto) {
        try {
            Producto nuevoProducto = productoService.guardarProducto(producto);
            return ResponseEntity.ok(convertirADTO(nuevoProducto));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }


    private ProductoDTO convertirADTO(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());


        if (producto.getPrecio() != null) {
            dto.setPrecio(BigDecimal.valueOf(producto.getPrecio().doubleValue()));
        }
        if (producto.getPrecioOferta() != null) {
            dto.setPrecioOferta(BigDecimal.valueOf(producto.getPrecioOferta().doubleValue()));
        }

        dto.setEnOferta(producto.isEnOferta());
        dto.setCategoriaId(producto.getCategoria() != null ? producto.getCategoria().getId() : null);
        dto.setCategoriaNombre(producto.getCategoria() != null ? producto.getCategoria().getNombre() : null);
        dto.setImagenUrl(producto.getImagenUrl());
        dto.setStock(producto.getStock());
        dto.setLaboratorio(producto.getLaboratorio());
        dto.setPrincipioActivo(producto.getPrincipioActivo());
        dto.setCodigoBarras(producto.getCodigoBarras());
        dto.setRequiereReceta(producto.isRequiereReceta());
        dto.setActivo(producto.isActivo());

        return dto;
    }

}