package com.example.demo_app_vehiculos.controller;

import com.example.demo_app_vehiculos.model.Pesaje;
import com.example.demo_app_vehiculos.model.SolicitudPesaje;
import com.example.demo_app_vehiculos.report.ReportePesajePDFGenerator;
import com.example.demo_app_vehiculos.dto.ReportePesajeDTO;
import com.example.demo_app_vehiculos.service.BalanzaService;
import com.example.demo_app_vehiculos.service.PesajeService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.demo_app_vehiculos.model.Usuario;
import org.springframework.ui.Model;


import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/pesajes")
public class PesajeController {

    private final PesajeService pesajeService;
    private final BalanzaService balanzaService;
    private final ReportePesajePDFGenerator reportePesajePDFGenerator;

    public PesajeController(PesajeService pesajeService,
                            BalanzaService balanzaService,
                            ReportePesajePDFGenerator reportePesajePDFGenerator) {
        this.pesajeService = pesajeService;
        this.balanzaService = balanzaService;
        this.reportePesajePDFGenerator = reportePesajePDFGenerator;
    }

    // Mostrar todos los pesajes
    @GetMapping
    public String listarPesajes(Model model) {
        model.addAttribute("pesajes", pesajeService.listarTodos());
        return "listarPesajes";
    }

    // Registrar peso y generar PDF
    @PostMapping("/registrar/{id}")
    public void registrarPeso(@PathVariable Long id,
                              @RequestParam Double peso,
                              HttpServletResponse response) throws Exception {

        // 1. Buscar la solicitud
        SolicitudPesaje solicitud = balanzaService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada con ID: " + id));

        // 2. Crear y guardar el Pesaje
        Pesaje pesaje = new Pesaje();
        pesaje.setPlacaVehiculo(solicitud.getPlacaVehiculo());
        pesaje.setTipoVehiculo(solicitud.getTipoVehiculo());
        pesaje.setObservaciones(solicitud.getObservaciones());
        pesaje.setUsuario(solicitud.getUsuario());
        pesaje.setPesoTotal(peso);
        pesaje.setFechaRegistro(LocalDateTime.now());

        pesajeService.guardar(pesaje);

        // 3. Preparar DTO para el PDF
        ReportePesajeDTO dto = new ReportePesajeDTO();
        dto.setPlacaVehiculo(pesaje.getPlacaVehiculo());
        dto.setTipoVehiculo(pesaje.getTipoVehiculo());
        dto.setObservaciones(pesaje.getObservaciones());
        dto.setPesoTotal(pesaje.getPesoTotal());
        dto.setNombreUsuario(pesaje.getUsuario().getNombre());
        dto.setFechaRegistro(pesaje.getFechaRegistro());

        // 4. Configurar la respuesta para descargar PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=pesaje_" + pesaje.getId() + ".pdf");

        // 5. Generar el PDF
        reportePesajePDFGenerator.generarReportePesaje(response.getOutputStream(), List.of(dto));
    }
}
