package com.farmacia.service;

import com.farmacia.model.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaService {

    List<Categoria> obtenerTodasLasCategorias();

    List<Categoria> obtenerCategoriasRaiz();

    List<Categoria> obtenerSubcategorias(String categoriaPadreId);

    List<Categoria> buscarCategoriasPorNombre(String nombre);

    Optional<Categoria> obtenerCategoriaPorId(String id);

    Optional<Categoria> obtenerCategoriaPorNombre(String nombre);

    List<Categoria> buscarCategoriasPorKeyword(String keyword);

    Categoria guardarCategoria(Categoria categoria);

    void desactivarCategoria(String id);
}
