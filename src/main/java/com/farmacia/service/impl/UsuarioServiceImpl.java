package com.farmacia.service.impl;

import com.farmacia.Enum.Rol;
import com.farmacia.dto.LoginDTO;
import com.farmacia.dto.LoginRespuesta;
import com.farmacia.dto.RegistroUsuarioDTO;
import com.farmacia.model.Usuario;
import com.farmacia.repository.UsuarioRepository;
import com.farmacia.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepositorio;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Usuario registrarUsuario(RegistroUsuarioDTO datosRegistro) {
        // Verificar si el correo ya está registrado
        if (usuarioRepositorio.findByEmail(datosRegistro.getEmail()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado.");
        }

        Usuario usuario = new Usuario(
                datosRegistro.getNombre(),
                datosRegistro.getApellido(),
                datosRegistro.getEmail(),
                datosRegistro.getTelefono(),
                passwordEncoder.encode(datosRegistro.getContraseña()), // encode aquí
                Rol.CLIENTE
        );
        return usuarioRepositorio.save(usuario);
    }

    @Override
    public LoginRespuesta iniciarSesion(LoginDTO loginDTO) {
        Optional<Usuario> usuarioOpt = usuarioRepositorio.findByEmail(loginDTO.getEmail());

        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("El correo ingresado no existe.");
        }

        Usuario usuario = usuarioOpt.get();

        if (!passwordEncoder.matches(loginDTO.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta.");
        }

        return new LoginRespuesta(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getRol()
        );
    }
    @Override
    public boolean existePorEmail(String email) {
        return usuarioRepositorio.findByEmail(email).isPresent();
    }
}
