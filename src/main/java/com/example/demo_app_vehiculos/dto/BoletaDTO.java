package com.example.demo_app_vehiculos.dto;

public class BoletaDTO {
    private String cliente;
    private String producto;
    private double precio;
    private int cantidad;
    private double total;

    public BoletaDTO(String cliente, String producto, double precio, int cantidad) {
        this.cliente = cliente;
        this.producto = producto;
        this.precio = precio;
        this.cantidad = cantidad;
        this.total = precio * cantidad;
    }

    public String getCliente() { return cliente; }
    public String getProducto() { return producto; }
    public double getPrecio() { return precio; }
    public int getCantidad() { return cantidad; }
    public double getTotal() { return total; }
}
