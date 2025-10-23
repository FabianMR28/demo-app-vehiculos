package com.example.demo_app_vehiculos.controller;

import com.example.demo_app_vehiculos.model.Auto;
import com.example.demo_app_vehiculos.service.AutoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/autos")
public class AutoController {

    private final AutoService autoService;

    public AutoController(AutoService autoService) {
        this.autoService = autoService;
    }

    @GetMapping
    public String listarAutos(Model model) {
        model.addAttribute("autos", autoService.listarTodos());
        return "vehiculos"; // busca vehiculos.html en templates
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("auto", new Auto());
        return "formAuto";
    }

    @PostMapping
    public String guardar(@ModelAttribute Auto auto) {
        autoService.guardar(auto);
        return "redirect:/autos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        autoService.eliminar(id);
        return "redirect:/autos";
    }
}
