package com.example.demo_app_vehiculos.controller;

import com.example.demo_app_vehiculos.model.Bicicleta;
import com.example.demo_app_vehiculos.model.Usuario;
import com.example.demo_app_vehiculos.model.VentaBicicleta;
import com.example.demo_app_vehiculos.repository.BicicletaRepository;
import com.example.demo_app_vehiculos.report.BoletaBicicletaPDFGenerator;
import com.example.demo_app_vehiculos.service.UsuarioService;
import com.example.demo_app_vehiculos.service.VentaBicicletaService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/ventas-bicicletas")
public class VentaBicicletaController {

    private final VentaBicicletaService ventaBicicletaService;
    private final UsuarioService usuarioService;
    private final BicicletaRepository bicicletaRepository;
    private final BoletaBicicletaPDFGenerator boletaPDFGenerator;

    public VentaBicicletaController(
            VentaBicicletaService ventaBicicletaService,
            UsuarioService usuarioService,
            BicicletaRepository bicicletaRepository,
            BoletaBicicletaPDFGenerator boletaPDFGenerator
    ) {
        this.ventaBicicletaService = ventaBicicletaService;
        this.usuarioService = usuarioService;
        this.bicicletaRepository = bicicletaRepository;
        this.boletaPDFGenerator = boletaPDFGenerator;
    }

    // 🔹 GUARDAR venta y redirigir a la boleta
    @PostMapping("/guardar")
    public String guardarVenta(@RequestParam("bicicletaId") Long bicicletaId) {
        // 1️⃣ Obtener usuario autenticado
        Usuario usuario = usuarioService.obtenerUsuarioActual();
        if (usuario == null) {
            throw new RuntimeException("No hay usuario autenticado en la sesión.");
        }

        // 2️⃣ Buscar la bicicleta
        Bicicleta bicicleta = bicicletaRepository.findById(bicicletaId)
                .orElseThrow(() -> new RuntimeException("Bicicleta no encontrada con ID: " + bicicletaId));

        // 3️⃣ Crear y poblar la venta
        VentaBicicleta venta = new VentaBicicleta();
        venta.setUsuario(usuario);
        venta.setBicicleta(bicicleta);
        venta.setFecha(LocalDateTime.now()); // ✅ Mantiene LocalDateTime
        venta.setTotal(bicicleta.getPrecio());

        // 4️⃣ Guardar venta
        VentaBicicleta nuevaVenta = ventaBicicletaService.guardar(venta);
        if (nuevaVenta.getId() == null) {
            throw new RuntimeException("La venta no se guardó correctamente en la base de datos.");
        }

        // 5️⃣ Redirigir al PDF de la boleta
        return "redirect:/ventas-bicicletas/boleta/" + nuevaVenta.getId();
    }

    // 🔹 GENERAR BOLETA PDF
    @GetMapping("/boleta/{id}")
    public void generarBoleta(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        VentaBicicleta venta = ventaBicicletaService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Venta de bicicleta no encontrada con ID: " + id));

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=boleta_bicicleta_" + id + ".pdf");

        boletaPDFGenerator.generarBoleta(response.getOutputStream(), venta);
    }
}
