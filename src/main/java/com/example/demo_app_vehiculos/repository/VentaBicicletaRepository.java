package com.example.demo_app_vehiculos.repository;

import com.example.demo_app_vehiculos.model.VentaBicicleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaBicicletaRepository extends JpaRepository<VentaBicicleta, Long> {
}
