package com.example.demo_app_vehiculos.controller;

import com.example.demo_app_vehiculos.model.Auto;
import com.example.demo_app_vehiculos.service.AutoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/adminVehiculos")
@PreAuthorize("hasRole('ADMINISTRADOR')")
public class AdminVehiculoController {

    private final AutoService autoService;

    public AdminVehiculoController(AutoService autoService) {
        this.autoService = autoService;
    }

    // Mostrar lista de autos
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("autos", autoService.listarTodos());
        return "adminVehiculos";
    }

    // Formulario para nuevo auto
    @GetMapping("/nuevo")
    public String nuevoAuto(Model model) {
        model.addAttribute("auto", new Auto());
        return "formAuto";
    }

    // Guardar o actualizar auto
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Auto auto) {
        autoService.guardar(auto);
        return "redirect:/adminVehiculos";
    }

    // Editar auto
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Auto auto = autoService.obtenerPorId(id);
        model.addAttribute("auto", auto);
        return "formAuto";
    }

    // Eliminar auto
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        autoService.eliminar(id);
        return "redirect:/adminVehiculos";
    }
}
