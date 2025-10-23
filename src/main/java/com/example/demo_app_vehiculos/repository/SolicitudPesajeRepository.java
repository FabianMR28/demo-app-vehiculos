package com.example.demo_app_vehiculos.repository;

import com.example.demo_app_vehiculos.dto.ReportePesajeDTO;
import com.example.demo_app_vehiculos.model.SolicitudPesaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitudPesajeRepository extends JpaRepository<SolicitudPesaje, Long> {

    @Query("SELECT new com.example.demo_app_vehiculos.dto.ReportePesajeDTO(" +
           "s.placaVehiculo, s.tipoVehiculo, s.observaciones, s.pesoTotal, s.usuario.nombre, s.fechaRegistro) " +
           "FROM SolicitudPesaje s")
    List<ReportePesajeDTO> generarReportePesajeDTO();
}
