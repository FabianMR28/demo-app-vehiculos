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

@Controller
@RequestMapping("/balanzas")
public class BalanzaController {

    private final BalanzaService balanzaService;
    private final UsuarioService usuarioService;

    public BalanzaController(BalanzaService balanzaService, UsuarioService usuarioService) {
        this.balanzaService = balanzaService;
        this.usuarioService = usuarioService;
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

}
