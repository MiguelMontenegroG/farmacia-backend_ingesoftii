package com.farmacia.service.impl;

import com.farmacia.Enum.Rol;
import com.farmacia.dto.LoginDTO;
import com.farmacia.dto.LoginRespuesta;
import com.farmacia.dto.RegistroUsuarioDTO;
import com.farmacia.model.Usuario;
import com.farmacia.repository.UsuarioRepository;
import com.farmacia.security.JwtUtil;
import com.farmacia.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.HashSet;
import java.util.Set;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepositorio;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;
    
    // Almacenamiento temporal de tokens invalidados (en producción usar Redis o DB)
    private Set<String> invalidatedTokens = new HashSet<>();

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

        String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getRol().name(), usuario.getId());

        return new LoginRespuesta(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getRol(),
                token
        );
    }
    @Override
    public boolean existePorEmail(String email) {
        return usuarioRepositorio.findByEmail(email).isPresent();
    }

    @Override
    public void logout() {
        // Para autenticación sin estado, el cierre de sesión se maneja principalmente en el cliente
        // Este método puede usarse para cualquier limpieza del lado del servidor si es necesario
        // En una implementación más completa, se almacenarían los tokens invalidados
    }
    
    public void invalidateToken(String token) {
        invalidatedTokens.add(token);
    }
    
    public boolean isTokenInvalidated(String token) {
        return invalidatedTokens.contains(token);
    }
    
    // Implementación de métodos para gestión de cuenta
    @Override
    public Usuario actualizarUsuario(String id, Usuario usuario) {
        Optional<Usuario> usuarioExistente = usuarioRepositorio.findById(id);
        if (usuarioExistente.isPresent()) {
            Usuario u = usuarioExistente.get();
            u.setNombre(usuario.getNombre());
            u.setApellido(usuario.getApellido());
            u.setEmail(usuario.getEmail());
            u.setTelefono(usuario.getTelefono());
            // No actualizamos la contraseña aquí por seguridad
            return usuarioRepositorio.save(u);
        } else {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
    }
    
    @Override
    public Usuario obtenerUsuarioPorId(String id) {
        return usuarioRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }
}