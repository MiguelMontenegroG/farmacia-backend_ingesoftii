package com.farmacia.repository;

import com.farmacia.Enum.Rol;
import com.farmacia.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
    
    // Búsqueda por rol
    List<Usuario> findByRol(Rol rol);
    
    // Búsqueda de usuarios activos
    List<Usuario> findByActivo(boolean activo);
    
    // Búsqueda por rol y estado activo
    List<Usuario> findByRolAndActivo(Rol rol, boolean activo);
    
    // Contar usuarios por rol
    long countByRol(Rol rol);
    
    // Búsqueda por nombre o apellido
    @Query("{ $or: [ { 'nombre': { $regex: ?0, $options: 'i' } }, { 'apellido': { $regex: ?0, $options: 'i' } } ] }")
    List<Usuario> buscarPorNombreOApellido(String termino);
}

