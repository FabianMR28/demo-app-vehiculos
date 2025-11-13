package com.example.demo_app_vehiculos.service;

import com.example.demo_app_vehiculos.model.SolicitudSoporte;
import com.example.demo_app_vehiculos.model.Usuario;
import com.example.demo_app_vehiculos.repository.SolicitudSoporteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SoporteServiceTest {

    private SolicitudSoporteRepository soporteRepository;
    private SoporteService soporteService;

    @BeforeEach
    void setUp() {
        soporteRepository = Mockito.mock(SolicitudSoporteRepository.class);
        soporteService = new SoporteService(soporteRepository);
    }

    @Test
    void testListarSolicitudes() {
        when(soporteRepository.findAll()).thenReturn(List.of(new SolicitudSoporte()));

        List<SolicitudSoporte> resultado = soporteService.listarSolicitudes();

        assertFalse(resultado.isEmpty());
        verify(soporteRepository, times(1)).findAll();
    }

    @Test
    void testGuardar() {
        SolicitudSoporte soporte = new SolicitudSoporte();
        when(soporteRepository.save(soporte)).thenReturn(soporte);

        SolicitudSoporte resultado = soporteService.guardar(soporte);

        assertNotNull(resultado);
        verify(soporteRepository, times(1)).save(soporte);
    }

    @Test
    void testEliminar() {
        soporteService.eliminar(5L);
        verify(soporteRepository, times(1)).deleteById(5L);
    }

    @Test
    void testObtenerPorId() {
        SolicitudSoporte soporte = new SolicitudSoporte();
        soporte.setId(2L);

        when(soporteRepository.findById(2L)).thenReturn(Optional.of(soporte));

        SolicitudSoporte resultado = soporteService.obtenerPorId(2L);

        assertNotNull(resultado);
        assertEquals(2L, resultado.getId());
        verify(soporteRepository, times(1)).findById(2L);
    }

    @Test
    void testListarPorUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        when(soporteRepository.findByUsuario(usuario)).thenReturn(List.of(new SolicitudSoporte()));

        List<SolicitudSoporte> resultado = soporteService.listarPorUsuario(usuario);

        assertFalse(resultado.isEmpty());
        verify(soporteRepository, times(1)).findByUsuario(usuario);
    }
}
