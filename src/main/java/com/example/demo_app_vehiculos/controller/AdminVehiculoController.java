package com.example.demo_app_vehiculos.controller;

import com.example.demo_app_vehiculos.model.Auto;
import com.example.demo_app_vehiculos.service.AutoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String listar(Model model, @ModelAttribute("mensaje") String mensaje, 
                         @ModelAttribute("error") String error) {
        model.addAttribute("autos", autoService.listarTodos());
        if (mensaje != null) model.addAttribute("mensaje", mensaje);
        if (error != null) model.addAttribute("error", error);
        return "adminVehiculos";
    }

 // Nuevo vehículo (formulario vacío)
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("auto", new Auto()); // Objeto vacío
        model.addAttribute("titulo", "Agregar Vehículo");
        return "formAuto";
    }

    // Editar vehículo (formulario con datos)
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") Long id, Model model) {
        Auto auto = autoService.obtenerPorId(id);
        model.addAttribute("auto", auto); // Carga los datos del vehículo
        model.addAttribute("titulo", "Editar Vehículo");
        return "formAuto";
    }

    // Guardar (tanto nuevo como editado)
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Auto auto) {
        autoService.guardar(auto); // Si tiene ID → actualiza; si no → crea nuevo
        return "redirect:/adminVehiculos";
    }


    // Eliminar auto
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            autoService.eliminar(id);
            redirectAttributes.addFlashAttribute("mensaje", "✅ Vehículo eliminado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "❌ No se pudo eliminar el vehículo. Tiene una venta registrada");
        }
        return "redirect:/adminVehiculos";
    }

}
