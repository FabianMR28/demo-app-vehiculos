package com.example.demo_app_vehiculos.repository;

import com.example.demo_app_vehiculos.model.SolicitudSoporte;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitudSoporteRepository extends JpaRepository<SolicitudSoporte, Long> {
}
