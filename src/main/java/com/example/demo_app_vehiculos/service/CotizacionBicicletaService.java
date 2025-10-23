package com.example.demo_app_vehiculos.service;

import com.example.demo_app_vehiculos.model.CotizacionBicicleta;
import com.example.demo_app_vehiculos.repository.CotizacionBicicletaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CotizacionBicicletaService {

    private final CotizacionBicicletaRepository cotizacionBicicletaRepository;

    public CotizacionBicicletaService(CotizacionBicicletaRepository cotizacionBicicletaRepository) {
        this.cotizacionBicicletaRepository = cotizacionBicicletaRepository;
    }

    public List<CotizacionBicicleta> listar() {
        return cotizacionBicicletaRepository.findAll();
    }

    public CotizacionBicicleta guardar(CotizacionBicicleta cotizacion) {
        return cotizacionBicicletaRepository.save(cotizacion);
    }

    public void eliminar(Long id) {
        cotizacionBicicletaRepository.deleteById(id);
    }

    public CotizacionBicicleta obtenerPorId(Long id) {
        return cotizacionBicicletaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró la cotización de bicicleta con ID: " + id));
    }
}
