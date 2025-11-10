package com.farmacia.service.impl;

import com.farmacia.Enum.Rol;
import com.farmacia.dto.LoginDTO;
import com.farmacia.dto.LoginRespuesta;
import com.farmacia.dto.RegistroUsuarioDTO;
import com.farmacia.dto.ActualizarPerfilDTO;
import com.farmacia.dto.ActualizarDireccionDTO;
import com.farmacia.dto.UsuarioDetalleDTO;
import com.farmacia.dto.ActualizarUsuarioAdminDTO;
import com.farmacia.model.Usuario;
import com.farmacia.repository.UsuarioRepository;
import com.farmacia.security.JwtUtil;
import com.farmacia.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

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
        usuario.setActivo(true);
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setTotalCompras(0);
        usuario.setMontoTotalCompras(0.0);
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
    public Usuario obtenerUsuarioPorEmail(String email) {
        Optional<Usuario> usuarioOpt = usuarioRepositorio.findByEmail(email);
        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Usuario con email " + email + " no encontrado.");
        }
        return usuarioOpt.get();
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
    
    // ============ Métodos para gestión de cuenta (Cliente) ============
    
    @Override
    public Usuario actualizarUsuario(String id, Usuario usuario) {
        Optional<Usuario> usuarioExistente = usuarioRepositorio.findById(id);
        if (usuarioExistente.isPresent()) {
            Usuario u = usuarioExistente.get();
            u.setNombre(usuario.getNombre());
            u.setApellido(usuario.getApellido());
            u.setEmail(usuario.getEmail());
            u.setTelefono(usuario.getTelefono());
            u.setFechaUltimaActualizacion(LocalDateTime.now());
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
    
    @Override
    public Usuario actualizarPerfil(String id, ActualizarPerfilDTO perfilDTO) {
        Usuario usuario = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        
        usuario.setNombre(perfilDTO.getNombre());
        usuario.setApellido(perfilDTO.getApellido());
        usuario.setTelefono(perfilDTO.getTelefono());
        usuario.setFechaUltimaActualizacion(LocalDateTime.now());
        
        return usuarioRepositorio.save(usuario);
    }
    
    @Override
    public Usuario actualizarDireccion(String id, ActualizarDireccionDTO direccionDTO) {
        Usuario usuario = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        
        usuario.setDireccion(direccionDTO.getDireccion());
        usuario.setCiudad(direccionDTO.getCiudad());
        usuario.setEstado(direccionDTO.getEstado());
        usuario.setCodigoPostal(direccionDTO.getCodigoPostal());
        usuario.setFechaUltimaActualizacion(LocalDateTime.now());
        
        return usuarioRepositorio.save(usuario);
    }
    
    // ============ Métodos para gestión de usuarios (Admin) ============
    
    @Override
    public List<UsuarioDetalleDTO> listarUsuarios() {
        return usuarioRepositorio.findAll().stream()
                .map(this::convertToUsuarioDetalleDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<UsuarioDetalleDTO> listarClientesActivos() {
        return usuarioRepositorio.findByRolAndActivo(Rol.CLIENTE, true).stream()
                .map(this::convertToUsuarioDetalleDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public UsuarioDetalleDTO obtenerDetallesUsuario(String id) {
        Usuario usuario = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        return convertToUsuarioDetalleDTO(usuario);
    }
    
    @Override
    public Usuario actualizarUsuarioAdmin(String id, ActualizarUsuarioAdminDTO usuarioDTO) {
        Usuario usuario = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        
        // Validar que el nuevo email no esté en uso por otro usuario
        if (usuarioDTO.getEmail() != null && !usuarioDTO.getEmail().equals(usuario.getEmail())) {
            if (usuarioRepositorio.existsByEmail(usuarioDTO.getEmail())) {
                throw new RuntimeException("El email ya está registrado por otro usuario");
            }
            usuario.setEmail(usuarioDTO.getEmail());
        }
        
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setDireccion(usuarioDTO.getDireccion());
        usuario.setCiudad(usuarioDTO.getCiudad());
        usuario.setEstado(usuarioDTO.getEstado());
        usuario.setCodigoPostal(usuarioDTO.getCodigoPostal());
        usuario.setActivo(usuarioDTO.isActivo());
        usuario.setFechaUltimaActualizacion(LocalDateTime.now());
        
        return usuarioRepositorio.save(usuario);
    }
    
    @Override
    public void desactivarUsuario(String id) {
        Usuario usuario = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        usuario.setActivo(false);
        usuario.setFechaUltimaActualizacion(LocalDateTime.now());
        usuarioRepositorio.save(usuario);
    }
    
    @Override
    public void activarUsuario(String id) {
        Usuario usuario = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        usuario.setActivo(true);
        usuario.setFechaUltimaActualizacion(LocalDateTime.now());
        usuarioRepositorio.save(usuario);
    }
    
    @Override
    public List<UsuarioDetalleDTO> buscarUsuarios(String termino) {
        return usuarioRepositorio.buscarPorNombreOApellido(termino).stream()
                .map(this::convertToUsuarioDetalleDTO)
                .collect(Collectors.toList());
    }
    
    // ============ Métodos auxiliares ============
    
    private UsuarioDetalleDTO convertToUsuarioDetalleDTO(Usuario usuario) {
        return new UsuarioDetalleDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getTelefono(),
                usuario.getDireccion(),
                usuario.getCiudad(),
                usuario.getEstado(),
                usuario.getCodigoPostal(),
                usuario.getRol(),
                usuario.isActivo(),
                usuario.getFechaRegistro(),
                usuario.getFechaUltimaActualizacion(),
                usuario.getTotalCompras() != null ? usuario.getTotalCompras() : 0,
                usuario.getMontoTotalCompras() != null ? usuario.getMontoTotalCompras() : 0.0,
                usuario.getPrescripciones()
        );
    }
}