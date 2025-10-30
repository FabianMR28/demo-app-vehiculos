package com.example.demo_app_vehiculos.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "cotizaciones")
public class Cotizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fecha;
    private double precioEstimado;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "auto_id")
    private Auto auto;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public double getPrecioEstimado() { return precioEstimado; }
    public void setPrecioEstimado(double precioEstimado) { this.precioEstimado = precioEstimado; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Auto getAuto() { return auto; }
    public void setAuto(Auto auto) { this.auto = auto; }
}

