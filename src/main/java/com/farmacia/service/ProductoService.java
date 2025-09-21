package com.farmacia.service;

import com.farmacia.model.Producto;
import com.farmacia.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    // Obtener todos los productos activos (catálogo)
    public List<Producto> obtenerCatalogo() {
        return productoRepository.findByActivoTrue();
    }

    // Buscar productos por nombre
    public List<Producto> buscarProductosPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    // Buscar productos por categoría
    public List<Producto> buscarProductosPorCategoria(String categoriaId) {
        return productoRepository.findByCategoriaId(categoriaId);
    }

    // Buscar por principio activo
    public List<Producto> buscarPorPrincipioActivo(String principioActivo) {
        return productoRepository.findByPrincipioActivoContainingIgnoreCase(principioActivo);
    }

    // Buscar por laboratorio
    public List<Producto> buscarPorLaboratorio(String laboratorio) {
        return productoRepository.findByLaboratorioContainingIgnoreCase(laboratorio);
    }

    // Obtener producto por ID
    public Optional<Producto> obtenerProductoPorId(String id) {
        return productoRepository.findById(id);
    }

    // Obtener productos en oferta
    public List<Producto> obtenerProductosEnOferta() {
        return productoRepository.findByEnOfertaTrueAndActivoTrue();
    }

    // Guardar producto
    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    // Eliminar producto (soft delete)
    public void desactivarProducto(String id) {
        Optional<Producto> productoOpt = productoRepository.findById(id);
        productoOpt.ifPresent(producto -> {
            producto.setActivo(false);
            productoRepository.save(producto);
        });
    }
}