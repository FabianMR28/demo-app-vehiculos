package com.example.demo_app_vehiculos.repository;

import com.example.demo_app_vehiculos.model.Usuario;
import com.example.demo_app_vehiculos.model.VentaBicicleta;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaBicicletaRepository extends JpaRepository<VentaBicicleta, Long> {

    List<VentaBicicleta> findByUsuario(Usuario usuario);

}

