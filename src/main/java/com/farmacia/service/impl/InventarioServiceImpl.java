package com.farmacia.service.impl;

import com.farmacia.model.Producto;
import com.farmacia.repository.ProductoRepository;
import com.farmacia.service.InventarioService;
import com.farmacia.dto.InventarioDTO;
import com.farmacia.dto.ActualizarStockRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventarioServiceImpl implements InventarioService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public List<InventarioDTO> obtenerTodoElInventario() {
        return productoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public InventarioDTO obtenerProductoPorId(String id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
        return convertToDTO(producto);
    }

    @Override
    public List<InventarioDTO> buscarProductosPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public InventarioDTO aumentarStock(String id, ActualizarStockRequest request) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        producto.setStock(producto.getStock() + request.getCantidad());
        Producto productoActualizado = productoRepository.save(producto);

        return convertToDTO(productoActualizado);
    }

    @Override
    @Transactional
    public InventarioDTO disminuirStock(String id, ActualizarStockRequest request) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        int nuevoStock = producto.getStock() - request.getCantidad();
        if (nuevoStock < 0) {
            throw new RuntimeException("Stock no puede ser negativo. Stock actual: " + producto.getStock() +
                    ", intentando restar: " + request.getCantidad());
        }

        producto.setStock(nuevoStock);
        Producto productoActualizado = productoRepository.save(producto);

        return convertToDTO(productoActualizado);
    }

    private InventarioDTO convertToDTO(Producto producto) {
        return new InventarioDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getStock(),
                producto.getCategoria()
        );
    }
}