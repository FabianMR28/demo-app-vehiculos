package com.example.demo_app_vehiculos.service;

import com.example.demo_app_vehiculos.model.CotizacionBicicleta;
import com.example.demo_app_vehiculos.repository.CotizacionBicicletaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CotizacionBicicletaServiceTest {

    private CotizacionBicicletaRepository cotizacionBicicletaRepository;
    private CotizacionBicicletaService cotizacionBicicletaService;

    @BeforeEach
    void setUp() {
        cotizacionBicicletaRepository = Mockito.mock(CotizacionBicicletaRepository.class);
        cotizacionBicicletaService = new CotizacionBicicletaService(cotizacionBicicletaRepository);
    }

    @Test
    void testListar() {
        when(cotizacionBicicletaRepository.findAll()).thenReturn(List.of(new CotizacionBicicleta()));

        List<CotizacionBicicleta> resultado = cotizacionBicicletaService.listar();

        assertFalse(resultado.isEmpty());
        verify(cotizacionBicicletaRepository, times(1)).findAll();
    }

    @Test
    void testGuardar() {
        CotizacionBicicleta cotizacion = new CotizacionBicicleta();
        when(cotizacionBicicletaRepository.save(cotizacion)).thenReturn(cotizacion);

        CotizacionBicicleta resultado = cotizacionBicicletaService.guardar(cotizacion);

        assertNotNull(resultado);
        verify(cotizacionBicicletaRepository).save(cotizacion);
    }

    @Test
    void testEliminar() {
        cotizacionBicicletaService.eliminar(5L);
        verify(cotizacionBicicletaRepository).deleteById(5L);
    }

    @Test
    void testObtenerPorId_Encontrado() {
        CotizacionBicicleta cotizacion = new CotizacionBicicleta();
        cotizacion.setId(2L);

        when(cotizacionBicicletaRepository.findById(2L)).thenReturn(Optional.of(cotizacion));

        CotizacionBicicleta resultado = cotizacionBicicletaService.obtenerPorId(2L);

        assertNotNull(resultado);
        assertEquals(2L, resultado.getId());
        verify(cotizacionBicicletaRepository).findById(2L);
    }

    @Test
    void testObtenerPorId_NoEncontrado() {
        when(cotizacionBicicletaRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> cotizacionBicicletaService.obtenerPorId(99L));

        assertEquals("No se encontró la cotización de bicicleta con ID: 99", exception.getMessage());
        verify(cotizacionBicicletaRepository).findById(99L);
    }
}
