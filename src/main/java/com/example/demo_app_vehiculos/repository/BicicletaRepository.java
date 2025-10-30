package com.example.demo_app_vehiculos.repository;

import com.example.demo_app_vehiculos.model.Bicicleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BicicletaRepository extends JpaRepository<Bicicleta, Long> {
}

