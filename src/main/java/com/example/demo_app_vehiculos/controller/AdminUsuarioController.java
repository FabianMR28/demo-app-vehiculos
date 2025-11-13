package com.example.demo_app_vehiculos.controller;

import com.example.demo_app_vehiculos.model.Usuario;
import com.example.demo_app_vehiculos.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/adminUsuarios")
public class AdminUsuarioController {

    private final UsuarioService usuarioService;

    public AdminUsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // 🔹 Listar usuarios
    @GetMapping
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listarUsuarios());
        return "adminUsuarios"; // Vista de listado
    }

    // 🔹 Mostrar formulario para crear nuevo usuario
    @GetMapping("/nuevo")
    public String nuevoUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "formUsuarios"; // Vista del formulario
    }

    // 🔹 Editar usuario existente
    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable("id") Long id, Model model) {
        Optional<Usuario> usuario = usuarioService.buscarPorId(id);
        if (usuario.isPresent()) {
            model.addAttribute("usuario", usuario.get());
            return "formUsuarios";
        } else {
            return "redirect:/adminUsuarios?error=Usuario no encontrado";
        }
    }

    // 🔹 Guardar (nuevo o editado)
    @PostMapping("/guardar")
    public String guardarUsuario(
            @ModelAttribute Usuario usuario,
            @RequestParam(name = "nuevaPassword", required = false) String nuevaPassword) {

        // ✅ Mantener o actualizar contraseña
        if (nuevaPassword != null && !nuevaPassword.trim().isEmpty()) {
            usuario.setPassword(nuevaPassword);
        } else if (usuario.getId() != null) {
            usuarioService.buscarPorId(usuario.getId())
                    .ifPresent(u -> usuario.setPassword(u.getPassword()));
        }

        // ✅ Normalizar el rol para que se guarde como texto completo
        if ("ADMIN".equalsIgnoreCase(usuario.getRol())) {
            usuario.setRol("ADMINISTRADOR");
        } else if ("USER".equalsIgnoreCase(usuario.getRol()) || "USUARIO".equalsIgnoreCase(usuario.getRol())) {
            usuario.setRol("USUARIO");
        }

        usuarioService.guardar(usuario);
        return "redirect:/adminUsuarios?mensaje=Usuario guardado correctamente";
    }

    // 🔹 Eliminar usuario
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable("id") Long id) {
        try {
            usuarioService.eliminar(id);
            return "redirect:/adminUsuarios?mensaje=Usuario eliminado correctamente";
        } catch (Exception e) {
            // ✅ Captura de error por relaciones (ventas asociadas)
            return "redirect:/adminUsuarios?error=No se puede eliminar el usuario porque tiene registros asociados.";
        }
    }
}
