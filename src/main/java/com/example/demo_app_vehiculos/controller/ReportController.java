package com.example.demo_app_vehiculos.controller;

import com.example.demo_app_vehiculos.dto.ReportePesajeDTO;
import com.example.demo_app_vehiculos.model.Venta;
import com.example.demo_app_vehiculos.repository.VentaRepository;
import com.example.demo_app_vehiculos.report.BoletaPDFGenerator;
import com.example.demo_app_vehiculos.report.ReportePesajePDFGenerator;
import com.example.demo_app_vehiculos.service.PesajeService;
import com.itextpdf.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/reportes")
public class ReportController {

    private final BoletaPDFGenerator boletaPDFGenerator;
    private final ReportePesajePDFGenerator reportePesajePDFGenerator;
    private final VentaRepository ventaRepository;
    private final PesajeService pesajeService;

    public ReportController(
            BoletaPDFGenerator boletaPDFGenerator,
            ReportePesajePDFGenerator reportePesajePDFGenerator,
            VentaRepository ventaRepository,
            PesajeService pesajeService
    ) {
        this.boletaPDFGenerator = boletaPDFGenerator;
        this.reportePesajePDFGenerator = reportePesajePDFGenerator;
        this.ventaRepository = ventaRepository;
        this.pesajeService = pesajeService;
    }

    // 🔹 Generar reporte de Boleta (Ventas)
    @GetMapping("/boleta/{id}")
    public void generarBoleta(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=comprobante_" + id + ".pdf");

        boletaPDFGenerator.generarBoleta(response.getOutputStream(), venta);
    }

    // 🔹 Generar reporte de Pesaje (Solicitudes)
    @GetMapping("/pesaje")
    public void generarReportePesaje(HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=reporte_pesaje.pdf");

        List<ReportePesajeDTO> registros = pesajeService.obtenerReportePesaje();
        reportePesajePDFGenerator.generarReportePesaje(response.getOutputStream(), registros);
    }
}
