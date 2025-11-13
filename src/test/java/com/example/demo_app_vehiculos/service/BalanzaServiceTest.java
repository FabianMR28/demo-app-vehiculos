package com.example.demo_app_vehiculos.service;

import com.example.demo_app_vehiculos.model.SolicitudPesaje;
import com.example.demo_app_vehiculos.repository.SolicitudPesajeRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BalanzaServiceTest {

    private SolicitudPesajeRepository solicitudPesajeRepository;
    private BalanzaService balanzaService;

    @BeforeEach
    void setUp() {
        solicitudPesajeRepository = Mockito.mock(SolicitudPesajeRepository.class);
        balanzaService = new BalanzaService(solicitudPesajeRepository);
    }

    @Test
    void testListarSolicitudes() {
        when(solicitudPesajeRepository.findAll()).thenReturn(List.of(new SolicitudPesaje()));

        List<SolicitudPesaje> resultado = balanzaService.listarSolicitudes();

        assertFalse(resultado.isEmpty());
        verify(solicitudPesajeRepository, times(1)).findAll();
    }

    @Test
    void testBuscarPorId() {
        SolicitudPesaje sp = new SolicitudPesaje();
        sp.setId(1L);

        when(solicitudPesajeRepository.findById(1L)).thenReturn(Optional.of(sp));

        Optional<SolicitudPesaje> resultado = balanzaService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
        verify(solicitudPesajeRepository).findById(1L);
    }

    @Test
    void testGuardar() {
        SolicitudPesaje sp = new SolicitudPesaje();
        when(solicitudPesajeRepository.save(sp)).thenReturn(sp);

        SolicitudPesaje resultado = balanzaService.guardar(sp);

        assertNotNull(resultado);
        verify(solicitudPesajeRepository).save(sp);
    }

    @Test
    void testEliminar() {
        balanzaService.eliminar(10L);
        verify(solicitudPesajeRepository).deleteById(10L);
    }
}
