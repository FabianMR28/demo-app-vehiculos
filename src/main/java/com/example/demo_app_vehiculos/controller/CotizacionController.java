package com.example.demo_app_vehiculos.controller;

import com.example.demo_app_vehiculos.model.Auto;
import com.example.demo_app_vehiculos.model.Cotizacion;
import com.example.demo_app_vehiculos.model.Usuario;
import com.example.demo_app_vehiculos.service.AutoService;
import com.example.demo_app_vehiculos.service.CotizacionService;
import com.example.demo_app_vehiculos.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cotizaciones")
public class CotizacionController {

    private final CotizacionService cotizacionService;
    private final AutoService autoService;
    private final UsuarioService usuarioService;

    public CotizacionController(CotizacionService cotizacionService,
                                AutoService autoService,
                                UsuarioService usuarioService) {
        this.cotizacionService = cotizacionService;
        this.autoService = autoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("cotizaciones", cotizacionService.listarCotizaciones());
        return "cotizaciones";
    }

    @GetMapping("/nueva")
    public String formulario(@RequestParam("idAuto") Long idAuto, Model model) {
        Auto auto = autoService.obtenerPorId(idAuto);
        Usuario usuario = usuarioService.obtenerUsuarioActual(); // Simula el usuario logueado

        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setAuto(auto);
        cotizacion.setUsuario(usuario);

        model.addAttribute("cotizacion", cotizacion);
        return "formCotizacion";
    }

    @PostMapping
    public String guardar(@ModelAttribute Cotizacion cotizacion) {
        cotizacionService.guardar(cotizacion);
        return "redirect:/cotizaciones?exito";
    }
}
