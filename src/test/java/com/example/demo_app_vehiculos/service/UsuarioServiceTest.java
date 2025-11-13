package com.example.demo_app_vehiculos.service;

import com.example.demo_app_vehiculos.model.Usuario;
import com.example.demo_app_vehiculos.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    private UsuarioRepository usuarioRepository;
    private PasswordEncoder passwordEncoder;
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        usuarioRepository = Mockito.mock(UsuarioRepository.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        usuarioService = new UsuarioService(usuarioRepository, passwordEncoder);
    }

    @Test
    void testListarUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(List.of(new Usuario()));

        List<Usuario> resultado = usuarioService.listarUsuarios();

        assertFalse(resultado.isEmpty());
        verify(usuarioRepository).findAll();
    }

    @Test
    void testBuscarPorId() {
        Usuario u = new Usuario();
        u.setId(1L);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(u));

        Optional<Usuario> resultado = usuarioService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
        verify(usuarioRepository).findById(1L);
    }

    @Test
    void testBuscarPorEmail() {
        Usuario u = new Usuario();
        u.setEmail("test@correo.com");

        when(usuarioRepository.findByEmail("test@correo.com")).thenReturn(Optional.of(u));

        Optional<Usuario> resultado = usuarioService.buscarPorEmail("test@correo.com");

        assertTrue(resultado.isPresent());
        assertEquals("test@correo.com", resultado.get().getEmail());
        verify(usuarioRepository).findByEmail("test@correo.com");
    }

    @Test
    void testGuardar() {
        Usuario user = new Usuario();
        user.setPassword("1234");

        when(passwordEncoder.encode("1234")).thenReturn("ENCRIPTADO");
        when(usuarioRepository.save(user)).thenReturn(user);

        Usuario resultado = usuarioService.guardar(user);

        assertEquals("ENCRIPTADO", resultado.getPassword());
        verify(passwordEncoder).encode("1234");
        verify(usuarioRepository).save(user);
    }

    @Test
    void testEliminar() {
        usuarioService.eliminar(5L);
        verify(usuarioRepository).deleteById(5L);
    }

    @Test
    void testObtenerUsuarioActual() {
        Usuario user = new Usuario();
        user.setEmail("usuario@test.com");

        Authentication auth = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(auth);
        when(auth.isAuthenticated()).thenReturn(true);
        when(auth.getName()).thenReturn("usuario@test.com");
        when(usuarioRepository.findByEmail("usuario@test.com")).thenReturn(Optional.of(user));

        // Usar MockedStatic para SecurityContextHolder
        try (MockedStatic<SecurityContextHolder> mocked = Mockito.mockStatic(SecurityContextHolder.class)) {
            mocked.when(SecurityContextHolder::getContext).thenReturn(securityContext);

            Usuario resultado = usuarioService.obtenerUsuarioActual();

            assertNotNull(resultado);
            assertEquals("usuario@test.com", resultado.getEmail());
        }
    }

    @Test
    void testObtenerUsuarioActualSinAutenticacion() {
        try (MockedStatic<SecurityContextHolder> mocked = Mockito.mockStatic(SecurityContextHolder.class)) {
            mocked.when(SecurityContextHolder::getContext).thenReturn(null);

            assertThrows(RuntimeException.class, () -> usuarioService.obtenerUsuarioActual());
        }
    }
}
