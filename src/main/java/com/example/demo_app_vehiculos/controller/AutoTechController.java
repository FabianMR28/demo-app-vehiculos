package com.example.demo_app_vehiculos.controller;

import com.example.demo_app_vehiculos.model.Usuario;
import com.example.demo_app_vehiculos.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AutoTechController {

    private final UsuarioService usuarioService;

    public AutoTechController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // --- Login ---
    @GetMapping("/login")
    public String login() {
        return "login";  
    }

    // --- Registro ---
    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro"; 
    }

    @PostMapping("/registro")
    public String registrar(@ModelAttribute Usuario usuario) {
        usuarioService.guardar(usuario);
        return "redirect:/login";
    }
}

