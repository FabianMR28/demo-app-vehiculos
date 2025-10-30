package com.example.demo_app_vehiculos.dto;

import java.time.LocalDateTime;

public class ReportePesajeDTO {

    private String placaVehiculo;
    private String tipoVehiculo;
    private String observaciones;
    private Double pesoTotal;
    private String nombreUsuario;
    private LocalDateTime fechaRegistro;

    public ReportePesajeDTO() {}

    public ReportePesajeDTO(String placaVehiculo, String tipoVehiculo, String observaciones,
                            Double pesoTotal, String nombreUsuario, LocalDateTime fechaRegistro) {
        this.placaVehiculo = placaVehiculo;
        this.tipoVehiculo = tipoVehiculo;
        this.observaciones = observaciones;
        this.pesoTotal = pesoTotal;
        this.nombreUsuario = nombreUsuario;
        this.fechaRegistro = fechaRegistro;
    }

    // Getters y Setters
    public String getPlacaVehiculo() { return placaVehiculo; }
    public void setPlacaVehiculo(String placaVehiculo) { this.placaVehiculo = placaVehiculo; }

    public String getTipoVehiculo() { return tipoVehiculo; }
    public void setTipoVehiculo(String tipoVehiculo) { this.tipoVehiculo = tipoVehiculo; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public Double getPesoTotal() { return pesoTotal; }
    public void setPesoTotal(Double pesoTotal) { this.pesoTotal = pesoTotal; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}
