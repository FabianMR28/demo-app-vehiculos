package com.example.demo_app_vehiculos.service;

import com.example.demo_app_vehiculos.model.SoporteTecnico;
import com.example.demo_app_vehiculos.repository.SoporteTecnicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SoporteTecnicoService {

    private final SoporteTecnicoRepository soporteTecnicoRepository;

    public SoporteTecnicoService(SoporteTecnicoRepository soporteTecnicoRepository) {
        this.soporteTecnicoRepository = soporteTecnicoRepository;
    }

    public List<SoporteTecnico> listarSolicitudes() {
        return soporteTecnicoRepository.findAll();
    }

    public Optional<SoporteTecnico> buscarPorId(Long id) {
        return soporteTecnicoRepository.findById(id);
    }

    public SoporteTecnico guardar(SoporteTecnico soporte) {
        return soporteTecnicoRepository.save(soporte);
    }

    public void eliminar(Long id) {
        soporteTecnicoRepository.deleteById(id);
    }
    
    public void actualizarEstado(Long id, String nuevoEstado) {
        SoporteTecnico soporte = soporteTecnicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada con id: " + id));

        soporte.setEstado(nuevoEstado); // 🔹 asegúrate que tu entidad SoporteTecnico tenga el campo `estado`
        soporteTecnicoRepository.save(soporte);
    }
}

