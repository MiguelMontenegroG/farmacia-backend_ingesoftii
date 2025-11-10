package com.farmacia.service.impl;

import com.farmacia.model.Favorito;
import com.farmacia.repository.FavoritosRepository;
import com.farmacia.service.FavoritosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoritosServiceImpl implements FavoritosService {

    @Autowired
    private FavoritosRepository favoritosRepository;

    @Override
    public List<Favorito> obtenerFavoritosPorUsuario(String usuarioId) {
        return favoritosRepository.findByUsuarioIdOrderByFechaAgregadoDesc(usuarioId);
    }

    @Override
    public boolean esFavorito(String usuarioId, String productoId) {
        return favoritosRepository.findByUsuarioIdAndProductoId(usuarioId, productoId).isPresent();
    }

    @Override
    public void agregarAFavoritos(String usuarioId, String productoId) {
        if (!esFavorito(usuarioId, productoId)) {
            Favorito favorito = new Favorito(usuarioId, productoId);
            favoritosRepository.save(favorito);
        }
    }

    @Override
    public void eliminarDeFavoritos(String usuarioId, String productoId) {
        Optional<Favorito> favorito = favoritosRepository.findByUsuarioIdAndProductoId(usuarioId, productoId);
        favorito.ifPresent(favoritosRepository::delete);
    }

    @Override
    public long contarFavoritosPorUsuario(String usuarioId) {
        return favoritosRepository.countByUsuarioId(usuarioId);
    }
}