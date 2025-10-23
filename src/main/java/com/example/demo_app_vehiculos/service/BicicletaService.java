package com.example.demo_app_vehiculos.service;

import com.example.demo_app_vehiculos.model.Bicicleta;
import com.example.demo_app_vehiculos.repository.BicicletaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BicicletaService {

    private final BicicletaRepository bicicletaRepository;

    public BicicletaService(BicicletaRepository bicicletaRepository) {
        this.bicicletaRepository = bicicletaRepository;
    }

    public List<Bicicleta> listarTodos() {
        return bicicletaRepository.findAll();
    }

    public Optional<Bicicleta> buscarPorId(Long id) {
        return bicicletaRepository.findById(id);
    }

    public Bicicleta guardar(Bicicleta bicicleta) {
        return bicicletaRepository.save(bicicleta);
    }

    public void eliminar(Long id) {
        bicicletaRepository.deleteById(id);
    }
    
    public Bicicleta obtenerPorId(Long id) {
        return bicicletaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró la bicicleta con ID: " + id));
    }

    
}

