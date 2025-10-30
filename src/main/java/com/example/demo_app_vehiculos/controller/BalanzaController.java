package com.example.demo_app_vehiculos.controller;

import com.example.demo_app_vehiculos.model.SolicitudPesaje;
import com.example.demo_app_vehiculos.model.Usuario;
import com.example.demo_app_vehiculos.service.BalanzaService;
import com.example.demo_app_vehiculos.service.UsuarioService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import java.time.LocalDateTime;

import com.example.demo_app_vehiculos.dto.ReportePesajeDTO;


import com.example.demo_app_vehiculos.report.ReportePesajePDFGenerator;
import jakarta.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/balanzas")
public class BalanzaController {

    private final BalanzaService balanzaService;
    private final UsuarioService usuarioService;
    private final ReportePesajePDFGenerator reportePDF; // ✅

    public BalanzaController(BalanzaService balanzaService,
                             UsuarioService usuarioService,
                             ReportePesajePDFGenerator reportePDF) { // ✅ Inyección aquí
        this.balanzaService = balanzaService;
        this.usuarioService = usuarioService;
        this.reportePDF = reportePDF;
    }

    // 🧩 Mostrar formulario + listar solo solicitudes del usuario logueado
    @GetMapping("/nueva")
    public String mostrarFormulario(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // Obtener usuario logueado
        String username = userDetails.getUsername();
        Usuario usuario = usuarioService.buscarPorEmail(username).orElse(null);

        // Agregar objeto para el formulario
        model.addAttribute("solicitud", new SolicitudPesaje());

        // ⚠️ Agregar usuario logueado al modelo para que el botón se muestre solo a ADMIN
        model.addAttribute("usuarioLogeado", usuario);

        // Agregar lista de solicitudes del usuario
        model.addAttribute("solicitudes", balanzaService.listarPorUsuario(usuario));

        return "formBalanza"; // tu template
    }


    // 🧾 Guardar nueva solicitud de pesaje (asociando al usuario logueado)
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("solicitud") SolicitudPesaje solicitud,
                          @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        Usuario usuario = usuarioService.buscarPorEmail(username).orElse(null);

        solicitud.setUsuario(usuario);
        solicitud.setFechaRegistro(LocalDateTime.now());
        balanzaService.guardar(solicitud);

        return "redirect:/balanzas/nueva";
    }

    // ❌ Eliminar solicitud (solo si pertenece al usuario actual)
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        Usuario usuario = usuarioService.buscarPorEmail(username).orElse(null);

        SolicitudPesaje solicitud = balanzaService.buscarPorId(id).orElse(null);
        if (solicitud.getUsuario().getId().equals(usuario.getId())) {
            balanzaService.eliminar(id);
        }

        return "redirect:/balanzas/nueva";
    }
    
    
    @GetMapping("/adminSolicitudesPesaje")
    public String adminSolicitudes(Model model) {
        // Traer todas las solicitudes de pesaje
        List<SolicitudPesaje> solicitudes = balanzaService.listarSolicitudes();
        model.addAttribute("solicitudes", solicitudes);

        return "adminSolicitudesPesaje"; // nombre del template Thymeleaf
    }

    
    @PostMapping("/registrar/{id}")
    public String registrarPeso(@PathVariable("id") Long id,
                                @RequestParam("peso") Double peso) {

        SolicitudPesaje solicitud = balanzaService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada con ID: " + id));

        solicitud.setPesoTotal(peso);
        balanzaService.guardar(solicitud);

        return "redirect:/balanzas/adminSolicitudesPesaje";
    }
    
    @GetMapping("/reporte/pdf")
    public void generarReporte(HttpServletResponse response) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=reporte_pesaje.pdf");

        var registros = balanzaService.listarReportePesaje();
        reportePDF.generarReportePesaje(response.getOutputStream(), registros);
    }
    
    
    @GetMapping("/reporte/pdf/{id}")
    public void generarReporteIndividual(@PathVariable("id") Long id, HttpServletResponse response) throws Exception {

        var solicitud = balanzaService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        var registro = List.of(new ReportePesajeDTO(
                solicitud.getPlacaVehiculo(),
                solicitud.getTipoVehiculo(),
                solicitud.getObservaciones(),
                solicitud.getPesoTotal(),
                solicitud.getUsuario() != null ? solicitud.getUsuario().getNombre() : "SIN NOMBRE",
                solicitud.getFechaRegistro()
        ));

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=pesaje_" + solicitud.getPlacaVehiculo() + ".pdf");

        reportePDF.generarReportePesaje(response.getOutputStream(), registro);
    }


}
