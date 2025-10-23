package com.example.demo_app_vehiculos.model;

import jakarta.persistence.*;

@Entity
@Table(name = "bicicletas")
public class Bicicleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String marca;
    private String modelo; // <-- nuevo campo
    private String tipo; // montaña, ruta, eléctrica
    private double precio;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }   // <-- getter nuevo
    public void setModelo(String modelo) { this.modelo = modelo; } // <-- setter nuevo

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
}