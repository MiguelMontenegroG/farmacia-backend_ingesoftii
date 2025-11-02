package com.farmacia.service;


import com.farmacia.dto.LoginDTO;
import com.farmacia.dto.LoginRespuesta;
import com.farmacia.dto.RegistroUsuarioDTO;
import com.farmacia.model.Usuario;

import java.util.List;

public interface UsuarioService {

    Usuario registrarUsuario(RegistroUsuarioDTO datosRegistro);
    LoginRespuesta iniciarSesion(LoginDTO loginDTO);
    boolean existePorEmail(String email);
    void logout();
    boolean verificarCodigo(String email, String codigo);

    //Métodos para usuario
    Usuario obtenerPerfil(String email);
    Usuario actualizarUsuario(String id, Usuario datosActualizados);
    boolean cambiarPassword(String id, String nuevaPassword);
    void eliminarCuentaPropia(String id);

    //Métodos para admin
    List<Usuario> listarUsuarios();
    Usuario cambiarRol(String id, String nuevoRol);
    void eliminarUsuarioPorAdmin(String id);
}
