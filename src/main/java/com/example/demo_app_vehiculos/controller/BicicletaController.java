package com.example.demo_app_vehiculos.controller;

import com.example.demo_app_vehiculos.model.Bicicleta;
import com.example.demo_app_vehiculos.service.BicicletaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bicicletas")
public class BicicletaController {

    private final BicicletaService bicicletaService;

    public BicicletaController(BicicletaService bicicletaService) {
        this.bicicletaService = bicicletaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("bicicletas", bicicletaService.listarTodos());
        return "bicicletas";
    }

    @GetMapping("/nueva")
    public String formulario(Model model) {
        model.addAttribute("bicicleta", new Bicicleta());
        return "formBicicleta";
    }

    @PostMapping
    public String guardar(@ModelAttribute Bicicleta bicicleta) {
        bicicletaService.guardar(bicicleta);
        return "redirect:/bicicletas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        bicicletaService.eliminar(id);
        return "redirect:/bicicletas";
    }
}
