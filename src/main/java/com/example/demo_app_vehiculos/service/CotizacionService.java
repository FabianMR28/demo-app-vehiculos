package com.example.demo_app_vehiculos.service;

import com.example.demo_app_vehiculos.model.Cotizacion;
import com.example.demo_app_vehiculos.repository.CotizacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CotizacionService {

    private final CotizacionRepository cotizacionRepository;

    public CotizacionService(CotizacionRepository cotizacionRepository) {
        this.cotizacionRepository = cotizacionRepository;
    }

    public List<Cotizacion> listarCotizaciones() {
        return cotizacionRepository.findAll();
    }

    public Optional<Cotizacion> buscarPorId(Long id) {
        return cotizacionRepository.findById(id);
    }

    public Cotizacion guardar(Cotizacion cotizacion) {
        return cotizacionRepository.save(cotizacion);
    }

    public void eliminar(Long id) {
        cotizacionRepository.deleteById(id);
    }
}
