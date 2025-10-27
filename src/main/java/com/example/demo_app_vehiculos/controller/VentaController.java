package com.example.demo_app_vehiculos.controller;

import com.example.demo_app_vehiculos.model.Venta;
import com.example.demo_app_vehiculos.report.BoletaPDFGenerator;
import com.example.demo_app_vehiculos.service.VentaService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/ventas")
public class VentaController {

    private final VentaService ventaService;
    private final BoletaPDFGenerator boletaPDFGenerator;

    public VentaController(VentaService ventaService, BoletaPDFGenerator boletaPDFGenerator) {
        this.ventaService = ventaService;
        this.boletaPDFGenerator = boletaPDFGenerator;
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

    @PostMapping("/guardar")
    public String guardarVenta(@ModelAttribute Venta venta, RedirectAttributes redirectAttributes) {
        Venta nuevaVenta = ventaService.guardar(venta);

        // Redirige directamente a la descarga del PDF de esa venta
        return "redirect:/ventas/boleta/" + nuevaVenta.getId();
    }

    // 🔹 Generar boleta PDF de una venta específica
    @GetMapping("/boleta/{id}")
    public void generarBoleta(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        // Busca la venta usando el servicio
        Venta venta = ventaService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=comprobante_" + id + ".pdf");

        boletaPDFGenerator.generarBoleta(response.getOutputStream(), venta);
    }


}
