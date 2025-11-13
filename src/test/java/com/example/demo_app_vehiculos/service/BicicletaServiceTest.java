package com.example.demo_app_vehiculos.service;

import com.example.demo_app_vehiculos.model.Bicicleta;
import com.example.demo_app_vehiculos.repository.BicicletaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BicicletaServiceTest {

    private BicicletaRepository bicicletaRepository;
    private BicicletaService bicicletaService;

    @BeforeEach
    void setUp() {
        bicicletaRepository = Mockito.mock(BicicletaRepository.class);
        bicicletaService = new BicicletaService(bicicletaRepository);
    }

    @Test
    void testListarTodos() {
        when(bicicletaRepository.findAll()).thenReturn(List.of(new Bicicleta()));

        List<Bicicleta> resultado = bicicletaService.listarTodos();

        assertFalse(resultado.isEmpty());
        verify(bicicletaRepository, times(1)).findAll();
    }

    @Test
    void testBuscarPorId() {
        Bicicleta bicicleta = new Bicicleta();
        bicicleta.setId(1L);

        when(bicicletaRepository.findById(1L)).thenReturn(Optional.of(bicicleta));

        Optional<Bicicleta> resultado = bicicletaService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
        verify(bicicletaRepository).findById(1L);
    }

    @Test
    void testGuardar() {
        Bicicleta bicicleta = new Bicicleta();
        when(bicicletaRepository.save(bicicleta)).thenReturn(bicicleta);

        Bicicleta resultado = bicicletaService.guardar(bicicleta);

        assertNotNull(resultado);
        verify(bicicletaRepository).save(bicicleta);
    }

    @Test
    void testEliminar() {
        bicicletaService.eliminar(5L);
        verify(bicicletaRepository).deleteById(5L);
    }

    @Test
    void testObtenerPorId_Encontrado() {
        Bicicleta bicicleta = new Bicicleta();
        bicicleta.setId(2L);

        when(bicicletaRepository.findById(2L)).thenReturn(Optional.of(bicicleta));

        Bicicleta resultado = bicicletaService.obtenerPorId(2L);

        assertNotNull(resultado);
        assertEquals(2L, resultado.getId());
        verify(bicicletaRepository).findById(2L);
    }

    @Test
    void testObtenerPorId_NoEncontrado() {
        when(bicicletaRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> bicicletaService.obtenerPorId(99L));

        assertEquals("No se encontró la bicicleta con ID: 99", exception.getMessage());
        verify(bicicletaRepository).findById(99L);
    }
}
