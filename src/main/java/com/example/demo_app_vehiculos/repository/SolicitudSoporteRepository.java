package com.example.demo_app_vehiculos.repository;

import com.example.demo_app_vehiculos.model.SolicitudSoporte;
import com.example.demo_app_vehiculos.model.Usuario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitudSoporteRepository extends JpaRepository<SolicitudSoporte, Long> {
	
    
    List<SolicitudSoporte> findByUsuario(Usuario usuario);
	
	
}
