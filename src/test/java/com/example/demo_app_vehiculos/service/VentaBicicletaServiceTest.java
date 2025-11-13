package com.example.demo_app_vehiculos.service;

import com.example.demo_app_vehiculos.model.VentaBicicleta;
import com.example.demo_app_vehiculos.repository.VentaBicicletaRepository;
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

class VentaBicicletaServiceTest {

    @Mock
    private VentaBicicletaRepository ventaBicicletaRepository;

    @InjectMocks
    private VentaBicicletaService ventaBicicletaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarVentas() {
        VentaBicicleta venta1 = new VentaBicicleta();
        venta1.setId(1L);

        VentaBicicleta venta2 = new VentaBicicleta();
        venta2.setId(2L);

        when(ventaBicicletaRepository.findAll()).thenReturn(Arrays.asList(venta1, venta2));

        List<VentaBicicleta> resultado = ventaBicicletaService.listarVentas();

        assertEquals(2, resultado.size());
        verify(ventaBicicletaRepository, times(1)).findAll();
    }

    @Test
    void testBuscarPorId() {
        VentaBicicleta venta = new VentaBicicleta();
        venta.setId(1L);

        when(ventaBicicletaRepository.findById(1L)).thenReturn(Optional.of(venta));

        Optional<VentaBicicleta> resultado = ventaBicicletaService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
        verify(ventaBicicletaRepository, times(1)).findById(1L);
    }

    @Test
    void testGuardar() {
        VentaBicicleta venta = new VentaBicicleta();
        venta.setTotal(120.0);

        when(ventaBicicletaRepository.save(venta)).thenReturn(venta);

        VentaBicicleta resultado = ventaBicicletaService.guardar(venta);

        assertEquals(120.0, resultado.getTotal());
        verify(ventaBicicletaRepository, times(1)).save(venta);
    }

    @Test
    void testEliminar() {
        Long id = 1L;

        ventaBicicletaService.eliminar(id);

        verify(ventaBicicletaRepository, times(1)).deleteById(id);
    }
}
