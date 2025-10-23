package com.example.demo_app_vehiculos.service;

import com.example.demo_app_vehiculos.model.Auto;
import com.example.demo_app_vehiculos.repository.AutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutoService {

    private final AutoRepository autoRepository;

    public AutoService(AutoRepository autoRepository) {
        this.autoRepository = autoRepository;
    }

    public List<Auto> listarTodos() {
        return autoRepository.findAll();
    }

    public Auto obtenerPorId(Long id) {
        return autoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el auto con ID: " + id));
    }

    public Auto guardar(Auto auto) {
        return autoRepository.save(auto);
    }

    public void eliminar(Long id) {
        autoRepository.deleteById(id);
    }

    public List<Auto> buscarPorMarca(String marca) {
        return autoRepository.findByMarcaContainingIgnoreCase(marca);
    }

    public List<Auto> listarPorEstado(boolean usado) {
        return autoRepository.findByUsado(usado);
    }
}
