package com.example.demo_app_vehiculos.controller;

import com.example.demo_app_vehiculos.model.Usuario;
import com.example.demo_app_vehiculos.repository.UsuarioRepository;
import com.example.demo_app_vehiculos.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class CurrentUserAdvice {

    private final UsuarioRepository usuarioRepository;

    public CurrentUserAdvice(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @ModelAttribute("usuarioLogeado")
    public Usuario usuarioLogeado(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) return null;
        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            return ((CustomUserDetails) principal).getUsuario();
        }
        String username = authentication.getName();
        return usuarioRepository.findByEmail(username).orElse(null);
    }
}