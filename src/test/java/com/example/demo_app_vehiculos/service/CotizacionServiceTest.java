package com.example.demo_app_vehiculos.service;

import com.example.demo_app_vehiculos.model.Cotizacion;
import com.example.demo_app_vehiculos.repository.CotizacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CotizacionServiceTest {

    private CotizacionRepository cotizacionRepository;
    private CotizacionService cotizacionService;

    @BeforeEach
    void setUp() {
        cotizacionRepository = Mockito.mock(CotizacionRepository.class);
        cotizacionService = new CotizacionService(cotizacionRepository);
    }

    @Test
    void testListarCotizaciones() {
        when(cotizacionRepository.findAll()).thenReturn(List.of(new Cotizacion()));

        List<Cotizacion> resultado = cotizacionService.listarCotizaciones();

        assertFalse(resultado.isEmpty());
        verify(cotizacionRepository, times(1)).findAll();
    }

    @Test
    void testBuscarPorId() {
        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setId(1L);

        when(cotizacionRepository.findById(1L)).thenReturn(Optional.of(cotizacion));

        Optional<Cotizacion> resultado = cotizacionService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
        verify(cotizacionRepository).findById(1L);
    }

    @Test
    void testGuardar() {
        Cotizacion cotizacion = new Cotizacion();
        when(cotizacionRepository.save(cotizacion)).thenReturn(cotizacion);

        Cotizacion resultado = cotizacionService.guardar(cotizacion);

        assertNotNull(resultado);
        verify(cotizacionRepository).save(cotizacion);
    }

    @Test
    void testEliminar() {
        cotizacionService.eliminar(10L);
        verify(cotizacionRepository).deleteById(10L);
    }
}
