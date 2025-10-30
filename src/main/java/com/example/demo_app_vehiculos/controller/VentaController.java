package com.example.demo_app_vehiculos.controller;

import com.example.demo_app_vehiculos.model.Auto;
import com.example.demo_app_vehiculos.model.Usuario;
import com.example.demo_app_vehiculos.model.Venta;
import com.example.demo_app_vehiculos.report.BoletaPDFGenerator;
import com.example.demo_app_vehiculos.repository.AutoRepository;
import com.example.demo_app_vehiculos.service.UsuarioService;
import com.example.demo_app_vehiculos.service.VentaService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/ventas")
public class VentaController {

    private final VentaService ventaService;
    private final BoletaPDFGenerator boletaPDFGenerator;
    private final UsuarioService usuarioService;
    private final AutoRepository autoRepository;

    public VentaController(
            VentaService ventaService,
            BoletaPDFGenerator boletaPDFGenerator,
            UsuarioService usuarioService,
            AutoRepository autoRepository
    ) {
        this.ventaService = ventaService;
        this.boletaPDFGenerator = boletaPDFGenerator;
        this.usuarioService = usuarioService;
        this.autoRepository = autoRepository;
    }

    // 🔹 Listar ventas (solo si lo necesitas)
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("ventas", ventaService.listarVentas());
        return "ventaAuto";
    }

    // 🔹 Guardar venta cuando el usuario confirma la compra
    @PostMapping("/guardar")
    public String guardarVenta(@RequestParam("autoId") Long autoId, RedirectAttributes redirectAttributes) {

        // 1️⃣ Obtener usuario logueado
        Usuario usuario = usuarioService.obtenerUsuarioActual();
        if (usuario == null) {
            throw new RuntimeException("No se encontró un usuario autenticado.");
        }

        // 2️⃣ Buscar el auto
        Auto auto = autoRepository.findById(autoId)
                .orElseThrow(() -> new RuntimeException("Auto no encontrado con ID: " + autoId));

        // 3️⃣ Crear la venta
        Venta venta = new Venta();
        venta.setUsuario(usuario);
        venta.setAuto(auto);
        venta.setFecha(LocalDateTime.now());
        venta.setTotal(auto.getPrecio());

        // 4️⃣ Guardar en la base de datos
        Venta nuevaVenta = ventaService.guardar(venta);

        // 5️⃣ Redirigir a la boleta de esa venta recién creada
        return "redirect:/ventas/boleta/" + nuevaVenta.getId();
    }

    // 🔹 Generar boleta PDF
    @GetMapping("/boleta/{id}")
    public void generarBoleta(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        Venta venta = ventaService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=comprobante_" + id + ".pdf");

        boletaPDFGenerator.generarBoleta(response.getOutputStream(), venta);
    }
}
