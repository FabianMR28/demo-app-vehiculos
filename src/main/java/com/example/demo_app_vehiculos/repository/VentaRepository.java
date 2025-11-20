package com.example.demo_app_vehiculos.repository;

import com.example.demo_app_vehiculos.dto.BoletaDTO;
import com.example.demo_app_vehiculos.model.Usuario;
import com.example.demo_app_vehiculos.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

    @Query("SELECT new com.example.demo_app_vehiculos.dto.BoletaDTO(" +
           "v.usuario.nombre, v.auto.marca, v.auto.precio, v.total) " +
           "FROM Venta v")
    List<BoletaDTO> generarBoletaDTO();

    // ⭐ NECESARIO PARA FILTRAR POR USUARIO LOGEADO
    List<Venta> findByUsuario(Usuario usuario);
}
