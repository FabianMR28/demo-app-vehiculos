package com.example.demo_app_vehiculos.controller;

import com.example.demo_app_vehiculos.model.Usuario;
import com.example.demo_app_vehiculos.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/perfil")
public class PerfilController {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    public PerfilController(UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    // ============================================================
    // 1️⃣ MOSTRAR PERFIL
    // ============================================================
    @GetMapping
    public String mostrarPerfil(Model model) {
        Usuario usuario = usuarioService.obtenerUsuarioActual();
        model.addAttribute("usuario", usuario);
        return "perfil";
    }

    // ============================================================
    // 2️⃣ ACTUALIZAR PERFIL (nombre / email)
    // ============================================================
    @PostMapping("/actualizar")
    public String actualizarPerfil(
            @ModelAttribute("usuario") Usuario usuarioForm,
            Authentication authentication,
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        Usuario usuarioActual = usuarioService.obtenerUsuarioActual();

        usuarioActual.setNombre(usuarioForm.getNombre());
        usuarioActual.setEmail(usuarioForm.getEmail());

        usuarioService.guardarSinEncriptarPassword(usuarioActual);

        // 🔥 LOGOUT OBLIGATORIO AL ACTUALIZAR PERFIL
        new SecurityContextLogoutHandler().logout(request, response, authentication);

        return "redirect:/login?perfilActualizado";
    }

    // ============================================================
    // 3️⃣ CAMBIAR CONTRASEÑA
    // ============================================================
    @PostMapping("/cambiar-password")
    public String cambiarPassword(
            @RequestParam("passwordActual") String passwordActual,
            @RequestParam("passwordNueva") String passwordNueva,
            Authentication authentication,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model
    ) {

        Usuario usuario = usuarioService.obtenerUsuarioActual();

        // Validar contraseña actual
        if (!passwordEncoder.matches(passwordActual, usuario.getPassword())) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("errorPassword", "La contraseña actual es incorrecta");
            return "perfil";
        }

        // Guardar nueva password encriptada
        usuario.setPassword(passwordEncoder.encode(passwordNueva));
        usuarioService.guardarSinEncriptarPassword(usuario);

        // 🔥 LOGOUT OBLIGATORIO DESPUÉS DE CAMBIAR CONTRASEÑA
        new SecurityContextLogoutHandler().logout(request, response, authentication);

        return "redirect:/login?passwordActualizada";
    }
}
