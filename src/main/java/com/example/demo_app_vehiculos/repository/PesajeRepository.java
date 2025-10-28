package com.example.demo_app_vehiculos.repository;

import com.example.demo_app_vehiculos.model.Pesaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PesajeRepository extends JpaRepository<Pesaje, Long> {

    // Puedes agregar consultas personalizadas si quieres, por ejemplo:
    // List<Pesaje> findByPlacaVehiculo(String placa);
}
