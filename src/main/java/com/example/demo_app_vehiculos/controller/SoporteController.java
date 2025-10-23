package com.example.demo_app_vehiculos.controller;

import com.example.demo_app_vehiculos.model.SolicitudSoporte;
import com.example.demo_app_vehiculos.service.SoporteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/soportes")
public class SoporteController {

    private final SoporteService soporteService;

    public SoporteController(SoporteService soporteService) {
        this.soporteService = soporteService;
    }

    // Listar solicitudes
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("solicitudes", soporteService.listarSolicitudes());
        return "soportes"; // templates/soportes.html
    }

    // Mostrar formulario
    @GetMapping("/nueva")
    public String formulario(Model model) {
        model.addAttribute("soporte", new SolicitudSoporte());
        return "formSoporte";
    }

    // Guardar solicitud
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("soporte") SolicitudSoporte soporte, Model model) {
        soporte.setFechaRegistro(LocalDateTime.now());
        soporteService.guardar(soporte);

        // Reiniciar formulario y mostrar modal de confirmación
        model.addAttribute("soporte", new SolicitudSoporte());
        model.addAttribute("mostrarModal", true);

        return "formSoporte";
    }

    // Eliminar solicitud
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        soporteService.eliminar(id);
        return "redirect:/soportes";
    }
}
