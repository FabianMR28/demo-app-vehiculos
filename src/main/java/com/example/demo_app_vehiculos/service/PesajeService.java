package com.example.demo_app_vehiculos.service;

import com.example.demo_app_vehiculos.dto.ReportePesajeDTO;
import com.example.demo_app_vehiculos.model.Pesaje;
import com.example.demo_app_vehiculos.repository.PesajeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PesajeService {

    private final PesajeRepository pesajeRepository;

    public PesajeService(PesajeRepository pesajeRepository) {
        this.pesajeRepository = pesajeRepository;
    }

    // 🔹 Listar todas las solicitudes de pesaje
    public List<Pesaje> listarTodos() {
        return pesajeRepository.findAll();
    }

    // 🔹 Buscar una solicitud por ID
    public Optional<Pesaje> buscarPorId(Long id) {
        return pesajeRepository.findById(id);
    }

    // 🔹 Guardar o actualizar un pesaje
    public Pesaje guardar(Pesaje pesaje) {
        return pesajeRepository.save(pesaje);
    }

    // 🔹 Actualizar solo el peso registrado
    public Pesaje registrarPeso(Long id, Double pesoTotal) {
        Pesaje pesaje = pesajeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pesaje no encontrado con ID: " + id));
        pesaje.setPesoTotal(pesoTotal);
        return pesajeRepository.save(pesaje);
    }

    // 🔹 Obtener listado de DTOs para el reporte
    public List<ReportePesajeDTO> obtenerReportePesaje() {
        return pesajeRepository.findAll()
                .stream()
                .map(p -> new ReportePesajeDTO(
                        p.getPlacaVehiculo(),
                        p.getTipoVehiculo(),
                        p.getObservaciones(),
                        p.getPesoTotal(),
                        p.getUsuario() != null ? p.getUsuario().getNombre() : "N/A",
                        p.getFechaRegistro()
                ))
                .collect(Collectors.toList());
    }
}
