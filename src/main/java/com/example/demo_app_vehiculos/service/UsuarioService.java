package com.example.demo_app_vehiculos.service;

import com.example.demo_app_vehiculos.model.Usuario;
import com.example.demo_app_vehiculos.repository.UsuarioRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ============================================================
    // LISTAR, BUSCAR, ELIMINAR
    // ============================================================
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }

    // ============================================================
    // GUARDAR con encriptación SEGURA (NO re-encripta)
    // ============================================================
    public Usuario guardar(Usuario usuario) {

        // Evitar re-encriptar cuando ya está en BCrypt
        if (!esPasswordEncriptada(usuario.getPassword())) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        }

        return usuarioRepository.save(usuario);
    }

    // ============================================================
    // GUARDAR SIN encriptar (para actualizar nombre/email)
    // ============================================================
    public Usuario guardarSinEncriptarPassword(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // ============================================================
    // DETECTAR SI UNA CONTRASEÑA YA ESTÁ ENCRIPTADA
    // ============================================================
    private boolean esPasswordEncriptada(String password) {
        return password != null && (
                password.startsWith("$2a$")
                || password.startsWith("$2b$")
                || password.startsWith("$2y$")
        );
    }

    // ============================================================
    // OBTENER USUARIO LOGUEADO
    // ============================================================
    public Usuario obtenerUsuarioActual() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("No hay un usuario autenticado");
        }

        String email = auth.getName();

        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));
    }
}
