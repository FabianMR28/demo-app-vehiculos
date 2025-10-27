package com.example.demo_app_vehiculos.controller;

import com.example.demo_app_vehiculos.model.Bicicleta;
import com.example.demo_app_vehiculos.service.BicicletaService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/adminBicicletas")
@PreAuthorize("hasRole('ADMINISTRADOR')")
public class AdminBicicletaController {

    private final BicicletaService bicicletaService;

    public AdminBicicletaController(BicicletaService bicicletaService) {
        this.bicicletaService = bicicletaService;
    }

    // Mostrar lista de autos
    @GetMapping
    public String listar(Model model, @ModelAttribute("mensaje") String mensaje, 
                         @ModelAttribute("error") String error) {
        model.addAttribute("bicicletas", bicicletaService.listarTodos());
        if (mensaje != null) model.addAttribute("mensaje", mensaje);
        if (error != null) model.addAttribute("error", error);
        return "adminBicicletas";
    }

 // Nuevo vehículo (formulario vacío)
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("auto", new Bicicleta()); // Objeto vacío
        model.addAttribute("titulo", "Agregar Bicicleta");
        return "formBicicleta";
    }

    // Editar vehículo (formulario con datos)
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") Long id, Model model) {
        Bicicleta bicicleta = bicicletaService.obtenerPorId(id);
        model.addAttribute("bicicleta", bicicleta); // Carga los datos del vehículo
        model.addAttribute("titulo", "Editar Bicicleta");
        return "formBicicleta";
    }

    // Guardar (tanto nuevo como editado)
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Bicicleta bicicleta) {
    	bicicletaService.guardar(bicicleta); // Si tiene ID → actualiza; si no → crea nuevo
        return "redirect:/adminBicicletas";
    }


    // Eliminar auto
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
        	bicicletaService.eliminar(id);
            redirectAttributes.addFlashAttribute("mensaje", "✅ Bicicleta eliminado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "❌ No se pudo eliminar la bicicleta. Tiene una venta registrada");
        }
        return "redirect:/adminBicicletas";
    }

}
