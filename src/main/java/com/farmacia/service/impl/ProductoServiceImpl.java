package com.farmacia.service.impl;

import com.farmacia.dto.FiltroProductoDTO;
import com.farmacia.model.Oferta;
import com.farmacia.model.Producto;
import com.farmacia.repository.ProductoRepository;
import com.farmacia.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;


    @Override
    public List<Producto> obtenerCatalogo() {
        return productoRepository.findByActivoTrue();
    }


    @Override
    public List<Producto> buscarProductosPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }


    @Override
    public List<Producto> buscarProductosPorCategoria(String categoriaId) {
        return productoRepository.findByCategoriaId(categoriaId);
    }


    @Override
    public List<Producto> buscarPorPrincipioActivo(String principioActivo) {
        return productoRepository.findByPrincipioActivoContainingIgnoreCase(principioActivo);
    }


    @Override
    public List<Producto> buscarPorLaboratorio(String laboratorio) {
        return productoRepository.findByLaboratorioContainingIgnoreCase(laboratorio);
    }


    @Override
    public Optional<Producto> obtenerProductoPorId(String id) {
        return productoRepository.findById(id);
    }


    @Override
    public List<Producto> obtenerProductosEnOferta() {
        return productoRepository.findByEnOfertaTrueAndActivoTrue();
    }


    @Override
    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }


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

    @Override
    public List<Producto> filtrarProductos(FiltroProductoDTO filtros) {
        List<Producto> productos;

        // Búsqueda por término (nombre, descripción o principio activo)
        if (filtros.getBusqueda() != null && !filtros.getBusqueda().trim().isEmpty()) {
            productos = productoRepository.buscarPorTermino(filtros.getBusqueda());
        } else if (filtros.getCategoriaId() != null && !filtros.getCategoriaId().trim().isEmpty()) {
            productos = productoRepository.findByCategoriaId(filtros.getCategoriaId());
        } else {
            productos = productoRepository.findByActivoTrue();
        }


        if (filtros.getPrecioMin() != null) {
            BigDecimal min = BigDecimal.valueOf(filtros.getPrecioMin());
            productos = productos.stream()
                    .filter(p -> getPrecioFinal(p).compareTo(min) >= 0)
                    .collect(Collectors.toList());
        }


        if (filtros.getPrecioMax() != null) {
            BigDecimal max = BigDecimal.valueOf(filtros.getPrecioMax());
            productos = productos.stream()
                    .filter(p -> getPrecioFinal(p).compareTo(max) <= 0)
                    .collect(Collectors.toList());
        }


        if (filtros.getRequiereReceta() != null) {
            boolean requiere = filtros.getRequiereReceta();
            productos = productos.stream()
                    .filter(p -> p.isRequiereReceta() == requiere)
                    .collect(Collectors.toList());
        }


        if (filtros.getActivo() != null) {
            boolean activo = filtros.getActivo();
            productos = productos.stream()
                    .filter(p -> p.isActivo() == activo)
                    .collect(Collectors.toList());
        }

        if (filtros.getOrdenarPor() != null) {
            switch (filtros.getOrdenarPor()) {
                case "precio-asc":
                    productos.sort((a, b) -> getPrecioFinal(a).compareTo(getPrecioFinal(b)));
                    break;
                case "precio-desc":
                    productos.sort((a, b) -> getPrecioFinal(b).compareTo(getPrecioFinal(a)));
                    break;
                case "nombre":
                    productos.sort((a, b) -> a.getNombre().compareToIgnoreCase(b.getNombre()));
                    break;
                case "rating":

                    productos.sort((a, b) -> {

                        return a.getNombre().compareToIgnoreCase(b.getNombre());
                    });
                    break;
            }
        }

        return productos;
    }

    private BigDecimal getPrecioFinal(Producto producto) {
        if (producto.isEnOferta() && producto.getPrecioOferta() != null
                && producto.getPrecioOferta().compareTo(BigDecimal.ZERO) > 0) {
            return producto.getPrecioOferta();
        }

        return producto.getPrecio();
    }
    
    // Implementación del método adicional necesario para AdminController
    @Override
    public List<Producto> obtenerProductos() {
        return productoRepository.findAll();
    }
    
    // Implementación de métodos para gestión de ofertas
    @Override
    public List<Oferta> obtenerTodasLasOfertas() {
        // Esta implementación es un placeholder
        // En una implementación completa, se conectaría con un repositorio de ofertas
        return List.of();
    }
    
    @Override
    public Oferta crearOferta(Oferta oferta) {
        // Esta implementación es un placeholder
        // En una implementación completa, se guardaría la oferta en un repositorio
        return oferta;
    }
    
    @Override
    public Oferta actualizarOferta(String id, Oferta oferta) {
        // Esta implementación es un placeholder
        // En una implementación completa, se actualizaría la oferta en un repositorio
        return oferta;
    }
    
    @Override
    public void eliminarOferta(String id) {
        // Esta implementación es un placeholder
        // En una implementación completa, se eliminaría la oferta de un repositorio
    }
}