package com.farmacia.service;


import com.farmacia.dto.LoginDTO;
import com.farmacia.dto.LoginRespuesta;
import com.farmacia.dto.RegistroUsuarioDTO;
import com.farmacia.model.Usuario;

public interface UsuarioService {

    Usuario registrarUsuario(RegistroUsuarioDTO datosRegistro);
    LoginRespuesta iniciarSesion(LoginDTO loginDTO);
    boolean existePorEmail(String email);

}
