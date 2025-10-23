package com.example.demo_app_vehiculos.controller;

import com.example.demo_app_vehiculos.report.BoletaPDFGenerator;
import com.example.demo_app_vehiculos.report.ReportePesajePDFGenerator;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/reportes")
public class ReportController {

    private final BoletaPDFGenerator boletaPDFGenerator;
    private final ReportePesajePDFGenerator reportePesajePDFGenerator;

    public ReportController(BoletaPDFGenerator boletaPDFGenerator,
                            ReportePesajePDFGenerator reportePesajePDFGenerator) {
        this.boletaPDFGenerator = boletaPDFGenerator;
        this.reportePesajePDFGenerator = reportePesajePDFGenerator;
    }

    // 🔹 Generar reporte de Boleta (Ventas)
    @GetMapping("/boleta")
    public void generarBoleta(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=boleta.pdf");
        boletaPDFGenerator.generarBoleta(response.getOutputStream());
    }

    // 🔹 Generar reporte de Pesaje (Solicitudes)
    @GetMapping("/pesaje")
    public void generarReportePesaje(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=reporte_pesaje.pdf");
        reportePesajePDFGenerator.generarReportePesaje(response.getOutputStream());
    }
}
