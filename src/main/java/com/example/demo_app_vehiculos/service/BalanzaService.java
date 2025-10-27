package com.example.demo_app_vehiculos.service;

import com.example.demo_app_vehiculos.model.SolicitudPesaje;
import com.example.demo_app_vehiculos.model.Usuario;
import com.example.demo_app_vehiculos.repository.SolicitudPesajeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BalanzaService {

    private final SolicitudPesajeRepository solicitudPesajeRepository;

    public BalanzaService(SolicitudPesajeRepository solicitudPesajeRepository) {
        this.solicitudPesajeRepository = solicitudPesajeRepository;
    }

    public List<SolicitudPesaje> listarSolicitudes() {
        return solicitudPesajeRepository.findAll();
    }

    public Optional<SolicitudPesaje> buscarPorId(Long id) {
        return solicitudPesajeRepository.findById(id);
    }

    public SolicitudPesaje guardar(SolicitudPesaje solicitud) {
        return solicitudPesajeRepository.save(solicitud);
    }

    public void eliminar(Long id) {
        solicitudPesajeRepository.deleteById(id);
    }
    
    public List<SolicitudPesaje> listarPorUsuario(Usuario usuario) {
        return solicitudPesajeRepository.findByUsuario(usuario);
    }
}

