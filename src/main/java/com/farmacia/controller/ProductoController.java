package com.farmacia.controller;


import com.farmacia.dto.FiltroProductoDTO;
import com.farmacia.dto.ProductoDTO;
import com.farmacia.model.Producto;
import com.farmacia.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:3000", "https://*.vercel.app"}, allowCredentials = "true")
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;


    @GetMapping("obtener-productos")
    public ResponseEntity<List<ProductoDTO>> getProductos(@ModelAttribute FiltroProductoDTO filtros) {
        try {
            List<ProductoDTO> productosDTO = productoService.filtrarProductos(filtros)
                    .stream()
                    .map(this::convertirADTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(productosDTO);

        } catch (Exception e) {
            e.printStackTrace();
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
        dto.setImagen(producto.getImagen());
        dto.setStock(producto.getStock());
        dto.setLaboratorio(producto.getLaboratorio());
        dto.setPrincipioActivo(producto.getPrincipioActivo());
        dto.setCodigoBarras(producto.getCodigoBarras());
        dto.setRequiereReceta(producto.isRequiereReceta());
        dto.setActivo(producto.isActivo());

        return dto;
    }

}