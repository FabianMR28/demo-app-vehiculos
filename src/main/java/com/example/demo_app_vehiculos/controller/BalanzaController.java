package com.example.demo_app_vehiculos.controller;

import com.example.demo_app_vehiculos.model.SolicitudPesaje;
import com.example.demo_app_vehiculos.service.BalanzaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/balanzas")
public class BalanzaController {

    private final BalanzaService balanzaService;

    public BalanzaController(BalanzaService balanzaService) {
        this.balanzaService = balanzaService;
    }

    // Listar solicitudes
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("solicitudes", balanzaService.listarSolicitudes());
        return "balanzas"; // templates/balanzas.html
    }

    // Mostrar formulario
    @GetMapping("/nueva")
    public String formulario(Model model) {
        model.addAttribute("solicitud", new SolicitudPesaje());
        return "formBalanza"; // templates/formBalanza.html
    }

    // Guardar nueva solicitud
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("solicitud") SolicitudPesaje solicitud, 
                          Model model) {
        solicitud.setFechaRegistro(LocalDateTime.now()); // fecha automática
        balanzaService.guardar(solicitud); // guarda en BD

        // Prepara un objeto vacío para no romper el form
        model.addAttribute("solicitud", new SolicitudPesaje());

        // Flag para mostrar modal en la vista
        model.addAttribute("mostrarModal", true);

        return "formBalanza"; 
        // renderiza el formulario nuevamente y activa el modal
    }

    // Eliminar solicitud
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        balanzaService.eliminar(id);
        return "redirect:/balanzas";
    }
}
