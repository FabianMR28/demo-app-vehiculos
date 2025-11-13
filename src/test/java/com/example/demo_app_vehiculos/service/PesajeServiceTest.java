package com.example.demo_app_vehiculos.service;

import com.example.demo_app_vehiculos.dto.ReportePesajeDTO;
import com.example.demo_app_vehiculos.model.Pesaje;
import com.example.demo_app_vehiculos.model.Usuario;
import com.example.demo_app_vehiculos.repository.PesajeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PesajeServiceTest {

    private PesajeRepository pesajeRepository;
    private PesajeService pesajeService;

    @BeforeEach
    void setUp() {
        pesajeRepository = Mockito.mock(PesajeRepository.class);
        pesajeService = new PesajeService(pesajeRepository);
    }

    @Test
    void testListarTodos() {
        when(pesajeRepository.findAll()).thenReturn(List.of(new Pesaje()));

        List<Pesaje> resultado = pesajeService.listarTodos();

        assertFalse(resultado.isEmpty());
        verify(pesajeRepository, times(1)).findAll();
    }

    @Test
    void testBuscarPorId() {
        Pesaje pesaje = new Pesaje();
        pesaje.setId(1L);

        when(pesajeRepository.findById(1L)).thenReturn(Optional.of(pesaje));

        Optional<Pesaje> resultado = pesajeService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
        verify(pesajeRepository).findById(1L);
    }

    @Test
    void testGuardar() {
        Pesaje pesaje = new Pesaje();
        when(pesajeRepository.save(pesaje)).thenReturn(pesaje);

        Pesaje resultado = pesajeService.guardar(pesaje);

        assertNotNull(resultado);
        verify(pesajeRepository).save(pesaje);
    }

    @Test
    void testRegistrarPeso() {
        Pesaje pesaje = new Pesaje();
        pesaje.setId(5L);
        pesaje.setPesoTotal(0.0);

        when(pesajeRepository.findById(5L)).thenReturn(Optional.of(pesaje));
        when(pesajeRepository.save(pesaje)).thenReturn(pesaje);

        Pesaje resultado = pesajeService.registrarPeso(5L, 1500.0);

        assertEquals(1500.0, resultado.getPesoTotal());
        verify(pesajeRepository).save(pesaje);
    }

    @Test
    void testObtenerReportePesaje() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan Pérez");

        Pesaje pesaje = new Pesaje();
        pesaje.setPlacaVehiculo("ABC-123");
        pesaje.setTipoVehiculo("Camión");
        pesaje.setObservaciones("Sin novedades");
        pesaje.setPesoTotal(2400.5);
        pesaje.setUsuario(usuario);
        pesaje.setFechaRegistro(LocalDateTime.now());

        when(pesajeRepository.findAll()).thenReturn(List.of(pesaje));

        List<ReportePesajeDTO> reporte = pesajeService.obtenerReportePesaje();

        assertEquals(1, reporte.size());
        assertEquals("ABC-123", reporte.get(0).getPlacaVehiculo());
        assertEquals("Juan Pérez", reporte.get(0).getNombreUsuario());
        verify(pesajeRepository).findAll();
    }
}
