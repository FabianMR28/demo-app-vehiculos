package com.example.demo_app_vehiculos.service;

import com.example.demo_app_vehiculos.model.SolicitudSoporte;
import com.example.demo_app_vehiculos.model.Usuario;
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

    public SolicitudSoporte guardar(SolicitudSoporte soporte) {
        return soporteRepository.save(soporte);
    }

    public void eliminar(Long id) {
        soporteRepository.deleteById(id);
    }
    
    public SolicitudSoporte obtenerPorId(Long id) {
        return soporteRepository.findById(id).orElse(null);
    }
    
    public List<SolicitudSoporte> listarPorUsuario(Usuario usuario) {
        return soporteRepository.findByUsuario(usuario);
    }
}
