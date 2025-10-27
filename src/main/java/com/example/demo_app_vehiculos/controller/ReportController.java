package com.example.demo_app_vehiculos.controller;

import com.example.demo_app_vehiculos.model.Venta;
import com.example.demo_app_vehiculos.repository.VentaRepository;
import com.example.demo_app_vehiculos.report.BoletaPDFGenerator;
import com.example.demo_app_vehiculos.report.ReportePesajePDFGenerator;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/reportes")
public class ReportController {

    private final BoletaPDFGenerator boletaPDFGenerator;
    private final ReportePesajePDFGenerator reportePesajePDFGenerator;
    private final VentaRepository ventaRepository; // ✅ Agregado

    public ReportController(
            BoletaPDFGenerator boletaPDFGenerator,
            ReportePesajePDFGenerator reportePesajePDFGenerator,
            VentaRepository ventaRepository // ✅ Inyectado
    ) {
        this.boletaPDFGenerator = boletaPDFGenerator;
        this.reportePesajePDFGenerator = reportePesajePDFGenerator;
        this.ventaRepository = ventaRepository;
    }

    // 🔹 Generar reporte de Boleta (Ventas)
    @GetMapping("/boleta/{id}")
    public void generarBoleta(@PathVariable("id") Long id , HttpServletResponse response) throws IOException {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=comprobante_" + id + ".pdf");

        boletaPDFGenerator.generarBoleta(response.getOutputStream(), venta);
    }

    // 🔹 Generar reporte de Pesaje (Solicitudes)
    @GetMapping("/pesaje")
    public void generarReportePesaje(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=reporte_pesaje.pdf");
        reportePesajePDFGenerator.generarReportePesaje(response.getOutputStream());
    }
}
