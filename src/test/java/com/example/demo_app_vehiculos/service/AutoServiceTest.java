package com.example.demo_app_vehiculos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.demo_app_vehiculos.model.Auto;
import com.example.demo_app_vehiculos.repository.AutoRepository;

class AutoServiceTest {

    private AutoRepository autoRepository;
    private AutoService autoService;

    @BeforeEach
    void setUp() {
        autoRepository = mock(AutoRepository.class);
        autoService = new AutoService(autoRepository);
    }

    @Test
    void listarTodos_retornaListaDeAutos() {
        Auto auto1 = new Auto();
        Auto auto2 = new Auto();
        when(autoRepository.findAll()).thenReturn(Arrays.asList(auto1, auto2));

        List<Auto> resultado = autoService.listarTodos();

        assertEquals(2, resultado.size());
        verify(autoRepository, times(1)).findAll();
    }

    @Test
    void obtenerPorId_siExiste_retornaAuto() {
        Auto auto = new Auto();
        auto.setId(1L);

        when(autoRepository.findById(1L)).thenReturn(Optional.of(auto));

        Auto resultado = autoService.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(autoRepository, times(1)).findById(1L);
    }

    @Test
    void obtenerPorId_siNoExiste_lanzaExcepcion() {
        when(autoRepository.findById(98L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> autoService.obtenerPorId(99L));

        assertEquals("No se encontró el auto con ID: 99", exception.getMessage());
    }

    @Test
    void guardar_guardaYRetornaAuto() {
        Auto auto = new Auto();
        auto.setMarca("Toyota");

        when(autoRepository.save(auto)).thenReturn(auto);

        Auto resultado = autoService.guardar(auto);

        assertEquals("Toyota", resultado.getMarca());
        verify(autoRepository, times(1)).save(auto);
    }

    @Test
    void eliminar_eliminaPorId() {
        autoService.eliminar(5L);
        verify(autoRepository, times(1)).deleteById(5L);
    }

    @Test
    void buscarPorMarca_retornaLista() {
        Auto auto = new Auto();
        auto.setMarca("Honda");

        when(autoRepository.findByMarcaContainingIgnoreCase("Hon"))
                .thenReturn(List.of(auto));

        List<Auto> resultado = autoService.buscarPorMarca("Hon");

        assertEquals(1, resultado.size());
        verify(autoRepository, times(1)).findByMarcaContainingIgnoreCase("Hon");
    }

    @Test
    void listarPorEstado_filtraPorUso() {
        Auto autoUsado = new Auto();
        autoUsado.setUsado(true);

        when(autoRepository.findByUsado(true)).thenReturn(List.of(autoUsado));

        List<Auto> resultado = autoService.listarPorEstado(true);

        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).isUsado());
        verify(autoRepository, times(1)).findByUsado(true);
    }
}
