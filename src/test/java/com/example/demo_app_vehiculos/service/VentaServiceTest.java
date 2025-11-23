package com.example.demo_app_vehiculos.service;

import com.example.demo_app_vehiculos.model.Venta;
import com.example.demo_app_vehiculos.repository.VentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VentaServiceTest {

    @Mock
    private VentaRepository ventaRepository;

    @InjectMocks
    private VentaService ventaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }	

    @Test
    void testListarVentas() {
        Venta v1 = new Venta();
        v1.setId(1L);

        Venta v2 = new Venta();
        v2.setId(2L);

        when(ventaRepository.findAll()).thenReturn(Arrays.asList(v1, v2));

        List<Venta> resultado = ventaService.listarVentas();

        assertEquals(2, resultado.size());
        verify(ventaRepository, times(1)).findAll();
    }

    @Test
    void testBuscarPorId() {
        Venta venta = new Venta();
        venta.setId(1L);

        when(ventaRepository.findById(1L)).thenReturn(Optional.of(venta));

        Optional<Venta> resultado = ventaService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
        verify(ventaRepository, times(1)).findById(1L);
    }

    @Test
    void testGuardar() {
        Venta venta = new Venta();
        venta.setTotal(150.0);

        when(ventaRepository.save(venta)).thenReturn(venta);

        Venta resultado = ventaService.guardar(venta);

        assertEquals(150.0, resultado.getTotal());
        verify(ventaRepository, times(1)).save(venta);
    }

    @Test
    void testEliminar() {
        Long id = 1L;

        ventaService.eliminar(id);

        verify(ventaRepository, times(1)).deleteById(id);
    }
}
