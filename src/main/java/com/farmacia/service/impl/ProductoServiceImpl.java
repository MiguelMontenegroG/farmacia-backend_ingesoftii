package com.farmacia.service.impl;

import com.farmacia.model.Producto;
import com.farmacia.repository.ProductoRepository;
import com.farmacia.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    // Obtener todos los productos activos (catálogo)
    @Override
    public List<Producto> obtenerCatalogo() {
        return productoRepository.findByActivoTrue();
    }

    // Buscar productos por nombre
    @Override
    public List<Producto> buscarProductosPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    // Buscar productos por categoría
    @Override
    public List<Producto> buscarProductosPorCategoria(String categoriaId) {
        return productoRepository.findByCategoriaId(categoriaId);
    }

    // Buscar por principio activo
    @Override
    public List<Producto> buscarPorPrincipioActivo(String principioActivo) {
        return productoRepository.findByPrincipioActivoContainingIgnoreCase(principioActivo);
    }

    // Buscar por laboratorio
    @Override
    public List<Producto> buscarPorLaboratorio(String laboratorio) {
        return productoRepository.findByLaboratorioContainingIgnoreCase(laboratorio);
    }

    // Obtener producto por ID
    @Override
    public Optional<Producto> obtenerProductoPorId(String id) {
        return productoRepository.findById(id);
    }

    // Obtener productos en oferta
    @Override
    public List<Producto> obtenerProductosEnOferta() {
        return productoRepository.findByEnOfertaTrueAndActivoTrue();
    }

    // Guardar producto
    @Override
    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    // Eliminar producto (soft delete)
    @Override
    public void desactivarProducto(String id) {
        Optional<Producto> productoOpt = productoRepository.findById(id);
        productoOpt.ifPresent(producto -> {
            producto.setActivo(false);
            productoRepository.save(producto);
        });
    }

    @Override
    public Producto obtenerPorId(String id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));
    }
}