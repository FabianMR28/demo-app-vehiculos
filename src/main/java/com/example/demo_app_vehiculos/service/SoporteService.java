package com.example.demo_app_vehiculos.service;

import com.example.demo_app_vehiculos.model.SolicitudSoporte;
import com.example.demo_app_vehiculos.repository.SolicitudSoporteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SoporteService {

    private final SolicitudSoporteRepository soporteRepository;

    public SoporteService(SolicitudSoporteRepository soporteRepository) {
        this.soporteRepository = soporteRepository;
    }

    public List<SolicitudSoporte> listarSolicitudes() {
        return soporteRepository.findAll();
    }

    public void guardar(SolicitudSoporte solicitud) {
        soporteRepository.save(solicitud);
    }

    public void eliminar(Long id) {
        soporteRepository.deleteById(id);
    }
}
