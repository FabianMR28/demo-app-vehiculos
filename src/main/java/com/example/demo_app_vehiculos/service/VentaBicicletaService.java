package com.example.demo_app_vehiculos.service;

import com.example.demo_app_vehiculos.model.VentaBicicleta;
import com.example.demo_app_vehiculos.repository.VentaBicicletaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VentaBicicletaService {

    private final VentaBicicletaRepository ventaBicicletaRepository;

    public VentaBicicletaService(VentaBicicletaRepository ventaBicicletaRepository) {
        this.ventaBicicletaRepository = ventaBicicletaRepository;
    }

    public List<VentaBicicleta> listarVentas() {
        return ventaBicicletaRepository.findAll();
    }

    public Optional<VentaBicicleta> buscarPorId(Long id) {
        return ventaBicicletaRepository.findById(id);
    }

    public VentaBicicleta guardar(VentaBicicleta venta) {
        return ventaBicicletaRepository.save(venta);
    }

    public void eliminar(Long id) {
        ventaBicicletaRepository.deleteById(id);
    }
}
