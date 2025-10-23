package com.example.demo_app_vehiculos.controller;

import com.example.demo_app_vehiculos.model.Bicicleta;
import com.example.demo_app_vehiculos.model.CotizacionBicicleta;
import com.example.demo_app_vehiculos.model.Usuario;
import com.example.demo_app_vehiculos.service.BicicletaService;
import com.example.demo_app_vehiculos.service.CotizacionBicicletaService;
import com.example.demo_app_vehiculos.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cotizaciones-bicicleta")
public class CotizacionBicicletaController {

    private final CotizacionBicicletaService cotizacionBicicletaService;
    private final BicicletaService bicicletaService;
    private final UsuarioService usuarioService;

    public CotizacionBicicletaController(CotizacionBicicletaService cotizacionBicicletaService,
                                         BicicletaService bicicletaService,
                                         UsuarioService usuarioService) {
        this.cotizacionBicicletaService = cotizacionBicicletaService;
        this.bicicletaService = bicicletaService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("cotizaciones", cotizacionBicicletaService.listar());
        return "cotizacionesBicicleta";
    }

    @GetMapping("/nueva")
    public String formulario(@RequestParam("idBicicleta") Long idBicicleta, Model model) {
        Bicicleta bicicleta = bicicletaService.obtenerPorId(idBicicleta);
        Usuario usuario = usuarioService.obtenerUsuarioActual();

        CotizacionBicicleta cotizacion = new CotizacionBicicleta();
        cotizacion.setBicicleta(bicicleta);
        cotizacion.setUsuario(usuario);

        model.addAttribute("cotizacion", cotizacion);
        return "formCotizacionBicicleta";
    }

    @PostMapping
    public String guardar(@ModelAttribute CotizacionBicicleta cotizacion) {
        cotizacionBicicletaService.guardar(cotizacion);
        return "redirect:/cotizaciones-bicicleta?exito";
    }
}
