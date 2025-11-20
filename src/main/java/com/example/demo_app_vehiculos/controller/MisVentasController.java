package com.example.demo_app_vehiculos.controller;

import com.example.demo_app_vehiculos.model.Usuario;
import com.example.demo_app_vehiculos.model.Venta;
import com.example.demo_app_vehiculos.model.VentaBicicleta;
import com.example.demo_app_vehiculos.service.UsuarioService;
import com.example.demo_app_vehiculos.service.VentaBicicletaService;
import com.example.demo_app_vehiculos.service.VentaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MisVentasController {

    private final UsuarioService usuarioService;
    private final VentaService ventaService;
    private final VentaBicicletaService ventaBicicletaService;

    public MisVentasController(UsuarioService usuarioService,
                               VentaService ventaService,
                               VentaBicicletaService ventaBicicletaService) {
        this.usuarioService = usuarioService;
        this.ventaService = ventaService;
        this.ventaBicicletaService = ventaBicicletaService;
    }

    @GetMapping("/mis-compras")
    public String listarMisVentas(Model model) {

        // 1️⃣ Obtener usuario actual
        Usuario usuario = usuarioService.obtenerUsuarioActual();
        if (usuario == null) {
            throw new RuntimeException("No hay usuario autenticado.");
        }

        // 2️⃣ Consultar ventas por usuario
        List<Venta> ventasAutos = ventaService.listarPorUsuario(usuario);
        List<VentaBicicleta> ventasBicicletas = ventaBicicletaService.listarPorUsuario(usuario);

        // 3️⃣ Enviar a la vista
        model.addAttribute("ventasAutos", ventasAutos);
        model.addAttribute("ventasBicicletas", ventasBicicletas);

        return "listaVentas";
    }
}
