package com.example.demo_app_vehiculos.repository;

import com.example.demo_app_vehiculos.model.SoporteTecnico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoporteTecnicoRepository extends JpaRepository<SoporteTecnico, Long> {
}
