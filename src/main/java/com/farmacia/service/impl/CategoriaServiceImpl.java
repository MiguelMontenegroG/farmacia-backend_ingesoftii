package com.farmacia.service.impl;

import com.farmacia.model.Categoria;
import com.farmacia.repository.CategoriaRepository;
import com.farmacia.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public List<Categoria> obtenerTodasLasCategorias() {
        return categoriaRepository.findByActivoTrue();
    }

    @Override
    public List<Categoria> obtenerCategoriasRaiz() {
        return categoriaRepository.findByCategoriaPadreIsNullAndActivoTrue();
    }

    @Override
    public List<Categoria> obtenerSubcategorias(String categoriaPadreId) {
        return categoriaRepository.findByCategoriaPadreId(categoriaPadreId);
    }

    @Override
    public List<Categoria> buscarCategoriasPorNombre(String nombre) {
        return categoriaRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public Optional<Categoria> obtenerCategoriaPorId(String id) {
        return categoriaRepository.findById(id);
    }

    @Override
    public Optional<Categoria> obtenerCategoriaPorNombre(String nombre) {
        return categoriaRepository.findByNombreAndActivoTrue(nombre);
    }

    @Override
    public List<Categoria> buscarCategoriasPorKeyword(String keyword) {
        return categoriaRepository.findByKeyword(keyword);
    }

    @Override
    public Categoria guardarCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public void desactivarCategoria(String id) {
        Optional<Categoria> categoriaOpt = categoriaRepository.findById(id);
        categoriaOpt.ifPresent(categoria -> {
            categoria.setActivo(false);
            categoriaRepository.save(categoria);
        });
    }
}
