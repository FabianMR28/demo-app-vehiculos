package com.example.demo_app_vehiculos.service;

import com.example.demo_app_vehiculos.model.SoporteTecnico;
import com.example.demo_app_vehiculos.repository.SoporteTecnicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SoporteTecnicoServiceTest {

    private SoporteTecnicoRepository soporteTecnicoRepository;
    private SoporteTecnicoService soporteTecnicoService;

    @BeforeEach
    void setUp() {
        soporteTecnicoRepository = Mockito.mock(SoporteTecnicoRepository.class);
        soporteTecnicoService = new SoporteTecnicoService(soporteTecnicoRepository);
    }

    @Test
    void testListarSolicitudes() {
        when(soporteTecnicoRepository.findAll()).thenReturn(List.of(new SoporteTecnico()));

        List<SoporteTecnico> resultado = soporteTecnicoService.listarSolicitudes();

        assertFalse(resultado.isEmpty());
        verify(soporteTecnicoRepository, times(1)).findAll();
    }

    @Test
    void testBuscarPorId() {
        SoporteTecnico soporte = new SoporteTecnico();
        soporte.setId(1L);

        when(soporteTecnicoRepository.findById(1L)).thenReturn(Optional.of(soporte));

        Optional<SoporteTecnico> resultado = soporteTecnicoService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
        verify(soporteTecnicoRepository, times(1)).findById(1L);
    }

    @Test
    void testGuardar() {
        SoporteTecnico soporte = new SoporteTecnico();
        when(soporteTecnicoRepository.save(soporte)).thenReturn(soporte);

        SoporteTecnico resultado = soporteTecnicoService.guardar(soporte);

        assertNotNull(resultado);
        verify(soporteTecnicoRepository, times(1)).save(soporte);
    }

    @Test
    void testEliminar() {
        soporteTecnicoService.eliminar(3L);
        verify(soporteTecnicoRepository, times(1)).deleteById(3L);
    }

    @Test
    void testActualizarEstado() {
        SoporteTecnico soporte = new SoporteTecnico();
        soporte.setId(5L);
        soporte.setEstado("Pendiente");

        when(soporteTecnicoRepository.findById(5L)).thenReturn(Optional.of(soporte));
        when(soporteTecnicoRepository.save(soporte)).thenReturn(soporte);

        soporteTecnicoService.actualizarEstado(5L, "Resuelto");

        assertEquals("Resuelto", soporte.getEstado());
        verify(soporteTecnicoRepository, times(1)).findById(5L);
        verify(soporteTecnicoRepository, times(1)).save(soporte);
    }

    @Test
    void testActualizarEstado_NotFound() {
        when(soporteTecnicoRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> soporteTecnicoService.actualizarEstado(99L, "Revisado"));

        assertEquals("Solicitud no encontrada con id: 99", exception.getMessage());
        verify(soporteTecnicoRepository, times(1)).findById(99L);
        verify(soporteTecnicoRepository, never()).save(any());
    }
}
