package com.example.demo_app_vehiculos.repository;


import com.example.demo_app_vehiculos.model.Auto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutoRepository extends JpaRepository<Auto, Long> {
    List<Auto> findByUsado(boolean usado); // Buscar autos nuevos/usados
    List<Auto> findByMarcaContainingIgnoreCase(String marca); // Buscar por marca
}

