package com.farmacia.service.impl;

import com.farmacia.Enum.Rol;
import com.farmacia.dto.LoginDTO;
import com.farmacia.dto.LoginRespuesta;
import com.farmacia.dto.RegistroUsuarioDTO;
import com.farmacia.model.Usuario;
import com.farmacia.repository.UsuarioRepository;
import com.farmacia.security.JwtUtil;
import com.farmacia.service.UsuarioService;
import com.farmacia.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepositorio;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmailService emailService;

    @Override
    public Usuario registrarUsuario(RegistroUsuarioDTO datosRegistro) {
        if (usuarioRepositorio.findByEmail(datosRegistro.getEmail()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado.");
        }

        // Generar código de verificación (4 dígitos)
        String codigo = String.format("%04d", new Random().nextInt(10000));

        Usuario usuario = new Usuario(
                datosRegistro.getNombre(),
                datosRegistro.getApellido(),
                datosRegistro.getEmail(),
                datosRegistro.getTelefono(),
                passwordEncoder.encode(datosRegistro.getContraseña()),
                Rol.CLIENTE
        );

        usuario.setVerificado(false);
        usuario.setCodigoVerificacion(codigo);

        usuarioRepositorio.save(usuario);

        // Enviar correo con el código
        emailService.enviarCodigo(usuario.getEmail(), codigo);

        return usuario;
    }

    @Override
    public LoginRespuesta iniciarSesion(LoginDTO loginDTO) {
        Optional<Usuario> usuarioOpt = usuarioRepositorio.findByEmail(loginDTO.getEmail());

        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("El correo ingresado no existe.");
        }

        Usuario usuario = usuarioOpt.get();

        if (!usuario.isVerificado()) {
            throw new RuntimeException("Tu cuenta aún no ha sido verificada. Revisa tu correo electrónico.");
        }

        if (!passwordEncoder.matches(loginDTO.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta.");
        }

        String token = jwtUtil.generateToken(usuario.getEmail(), usuario.getRol().name());

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
        // Stateless - manejado desde el front
    }

    @Override
    public boolean verificarCodigo(String email, String codigo) {
        Optional<Usuario> usuarioOpt = usuarioRepositorio.findByEmail(email);
        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado.");
        }

        Usuario usuario = usuarioOpt.get();

        if (usuario.getCodigoVerificacion() != null && usuario.getCodigoVerificacion().equals(codigo)) {
            usuario.setVerificado(true);
            usuario.setCodigoVerificacion(null);
            usuarioRepositorio.save(usuario);
            return true;
        } else {
            return false;
        }
    }
    @Override
    public Usuario obtenerPerfil(String email) {
        return usuarioRepositorio.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));
    }

    @Override
    public Usuario actualizarUsuario(String id, Usuario datosActualizados) {
        Usuario usuario = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        usuario.setNombre(datosActualizados.getNombre());
        usuario.setApellido(datosActualizados.getApellido());
        usuario.setTelefono(datosActualizados.getTelefono());
        usuario.setDireccion(datosActualizados.getDireccion());

        return usuarioRepositorio.save(usuario);
    }

    @Override
    public boolean cambiarPassword(String id, String nuevaPassword) {
        Usuario usuario = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        usuario.setPassword(passwordEncoder.encode(nuevaPassword));
        usuarioRepositorio.save(usuario);
        return true;
    }

    @Override
    public void eliminarCuentaPropia(String id) {
        Usuario usuario = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        usuarioRepositorio.delete(usuario);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepositorio.findAll();
    }

    @Override
    public Usuario cambiarRol(String id, String nuevoRol) {
        Usuario usuario = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        usuario.setRol(Rol.valueOf(nuevoRol.toUpperCase()));
        return usuarioRepositorio.save(usuario);
    }

    @Override
    public void eliminarUsuarioPorAdmin(String id) {
        Usuario usuario = usuarioRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        usuarioRepositorio.delete(usuario);
    }

}
