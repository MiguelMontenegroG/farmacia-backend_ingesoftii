package com.farmacia.service;

import com.farmacia.model.Categoria;
import com.farmacia.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Obtener todas las categorías activas
    public List<Categoria> obtenerTodasLasCategorias() {
        return categoriaRepository.findByActivoTrue();
    }

    // Obtener categorías raíz
    public List<Categoria> obtenerCategoriasRaiz() {
        return categoriaRepository.findByCategoriaPadreIsNullAndActivoTrue();
    }

    // Obtener subcategorías por categoría padre
    public List<Categoria> obtenerSubcategorias(String categoriaPadreId) {
        return categoriaRepository.findByCategoriaPadreId(categoriaPadreId);
    }

    // Buscar categorías por nombre
    public List<Categoria> buscarCategoriasPorNombre(String nombre) {
        return categoriaRepository.findByNombreContainingIgnoreCase(nombre);
    }

    // Obtener categoría por ID
    public Optional<Categoria> obtenerCategoriaPorId(String id) {
        return categoriaRepository.findById(id);
    }

    // Obtener categoría por nombre exacto
    public Optional<Categoria> obtenerCategoriaPorNombre(String nombre) {
        return categoriaRepository.findByNombreAndActivoTrue(nombre);
    }

    // Buscar categorías por keyword
    public List<Categoria> buscarCategoriasPorKeyword(String keyword) {
        return categoriaRepository.findByKeyword(keyword);
    }

    // Guardar categoría
    public Categoria guardarCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    // Eliminar categoría (soft delete)
    public void desactivarCategoria(String id) {
        Optional<Categoria> categoriaOpt = categoriaRepository.findById(id);
        categoriaOpt.ifPresent(categoria -> {
            categoria.setActivo(false);
            categoriaRepository.save(categoria);
        });
    }
}