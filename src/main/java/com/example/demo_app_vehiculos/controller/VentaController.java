package com.example.demo_app_vehiculos.controller;

import com.example.demo_app_vehiculos.model.Venta;
import com.example.demo_app_vehiculos.service.VentaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ventas")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("ventas", ventaService.listarVentas());
        return "ventaAuto";
    }

    @GetMapping("/nueva")
    public String formulario(Model model) {
        model.addAttribute("venta", new Venta());
        return "formVenta";
    }

    @PostMapping
    public String guardar(@ModelAttribute Venta venta) {
        ventaService.guardar(venta);
        return "redirect:/ventas";
    }
}

