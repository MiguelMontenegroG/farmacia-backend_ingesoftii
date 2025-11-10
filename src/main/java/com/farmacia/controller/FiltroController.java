package com.farmacia.controller;

import com.farmacia.dto.ApiResponse;
import com.farmacia.dto.FiltroProductoDTO;
import com.farmacia.model.Producto;
import com.farmacia.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/filtros")
@CrossOrigin(origins = "*")
public class FiltroController {

    @Autowired
    private ProductoService productoService;

    @PostMapping("/productos")
    public ResponseEntity<ApiResponse<List<Producto>>> filtrarProductos(@RequestBody FiltroProductoDTO filtros) {
        try {
            List<Producto> productos = productoService.filtrarProductos(filtros);
            return ResponseEntity.ok(new ApiResponse<>(true, "Productos filtrados exitosamente", productos));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                .body(new ApiResponse<>(false, "Error al filtrar productos: " + e.getMessage()));
        }
    }
}