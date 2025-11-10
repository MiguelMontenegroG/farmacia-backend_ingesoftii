package com.farmacia.service;


import com.farmacia.dto.LoginDTO;
import com.farmacia.dto.LoginRespuesta;
import com.farmacia.dto.RegistroUsuarioDTO;
import com.farmacia.dto.ActualizarPerfilDTO;
import com.farmacia.dto.ActualizarDireccionDTO;
import com.farmacia.dto.UsuarioDetalleDTO;
import com.farmacia.dto.ActualizarUsuarioAdminDTO;
import com.farmacia.model.Usuario;
import java.util.List;

public interface UsuarioService {

    Usuario registrarUsuario(RegistroUsuarioDTO datosRegistro);
    LoginRespuesta iniciarSesion(LoginDTO loginDTO);
    boolean existePorEmail(String email);
    Usuario obtenerUsuarioPorEmail(String email);
    void logout();
    
    // Métodos para gestión de cuenta (Cliente)
    Usuario actualizarUsuario(String id, Usuario usuario);
    Usuario obtenerUsuarioPorId(String id);
    Usuario actualizarPerfil(String id, ActualizarPerfilDTO perfilDTO);
    Usuario actualizarDireccion(String id, ActualizarDireccionDTO direccionDTO);
    
    // Métodos para gestión de usuarios (Admin)
    List<UsuarioDetalleDTO> listarUsuarios();
    List<UsuarioDetalleDTO> listarClientesActivos();
    UsuarioDetalleDTO obtenerDetallesUsuario(String id);
    Usuario actualizarUsuarioAdmin(String id, ActualizarUsuarioAdminDTO usuarioDTO);
    void desactivarUsuario(String id);
    void activarUsuario(String id);
    List<UsuarioDetalleDTO> buscarUsuarios(String termino);

}