package com.example.demo_app_vehiculos.controller;

import com.example.demo_app_vehiculos.model.SolicitudSoporte;
import com.example.demo_app_vehiculos.model.Usuario;
import com.example.demo_app_vehiculos.service.SoporteService;
import com.example.demo_app_vehiculos.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/soportes")
public class SoporteController {

    private final SoporteService soporteService;
    private final UsuarioService usuarioService;

    public SoporteController(SoporteService soporteService, UsuarioService usuarioService) {
        this.soporteService = soporteService;
        this.usuarioService = usuarioService;
    }

    // 📋 Listado general (solo si es necesario)
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("solicitudes", soporteService.listarSolicitudes());
        return "soportes";
    }

    // 📄 Formulario + lista del usuario actual
    @GetMapping("/nueva")
    public String formulario(Model model) {
        Usuario usuarioActual = usuarioService.obtenerUsuarioActual();

        List<SolicitudSoporte> solicitudesUsuario = soporteService.listarPorUsuario(usuarioActual);

        model.addAttribute("soporte", new SolicitudSoporte());
        model.addAttribute("solicitudesUsuario", solicitudesUsuario);
        return "formSoporte";
    }

    // 💾 Guardar solicitud
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("soporte") SolicitudSoporte soporte, Model model) {
        try {
            Usuario usuarioActual = usuarioService.obtenerUsuarioActual();
            soporte.setUsuario(usuarioActual);
            soporte.setFechaRegistro(LocalDateTime.now());
            soporteService.guardar(soporte);

            // Refrescar lista
            List<SolicitudSoporte> solicitudesUsuario = soporteService.listarPorUsuario(usuarioActual);
            model.addAttribute("soporte", new SolicitudSoporte());
            model.addAttribute("solicitudesUsuario", solicitudesUsuario);
            model.addAttribute("mostrarModal", true);

        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "formSoporte";
        }

        return "formSoporte";
    }

    // 🗑️ Eliminar solicitud
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        soporteService.eliminar(id);
        return "redirect:/soportes/nueva";
    }
}
