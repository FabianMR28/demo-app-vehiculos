package com.example.demo_app_vehiculos.service;

import com.example.demo_app_vehiculos.model.Usuario;
import com.example.demo_app_vehiculos.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ============================================================
    // TEST: listarUsuarios()
    // ============================================================
    @Test
    void testListarUsuarios() {
        List<Usuario> lista = List.of(new Usuario(), new Usuario());
        when(usuarioRepository.findAll()).thenReturn(lista);

        List<Usuario> result = usuarioService.listarUsuarios();

        assertEquals(2, result.size());
        verify(usuarioRepository, times(1)).findAll();
    }

    // ============================================================
    // TEST: buscarPorId()
    // ============================================================
    @Test
    void testBuscarPorId() {
        Usuario u = new Usuario();
        u.setId(1L);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(u));

        Optional<Usuario> result = usuarioService.buscarPorId(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    // ============================================================
    // TEST: buscarPorEmail()
    // ============================================================
    @Test
    void testBuscarPorEmail() {
        Usuario u = new Usuario();
        u.setEmail("test@mail.com");

        when(usuarioRepository.findByEmail("test@mail.com"))
                .thenReturn(Optional.of(u));

        Optional<Usuario> result = usuarioService.buscarPorEmail("test@mail.com");

        assertTrue(result.isPresent());
        assertEquals("test@mail.com", result.get().getEmail());
    }

    // ============================================================
    // TEST: eliminar()
    // ============================================================
    @Test
    void testEliminar() {
        usuarioService.eliminar(5L);
        verify(usuarioRepository, times(1)).deleteById(5L);
    }

    // ============================================================
    // TEST: guardar() ENCRIPTANDO LA CONTRASEÑA
    // ============================================================
    @Test
    void testGuardar_EncriptaPassword() {
        Usuario u = new Usuario();
        u.setPassword("1234");

        when(passwordEncoder.encode("1234")).thenReturn("ENCRIPTADA");
        when(usuarioRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Usuario result = usuarioService.guardar(u);

        assertEquals("ENCRIPTADA", result.getPassword());
        verify(passwordEncoder, times(1)).encode("1234");
        verify(usuarioRepository, times(1)).save(u);
    }

    // ============================================================
    // TEST: guardar() NO re-encripta si ya está en BCrypt
    // ============================================================
    @Test
    void testGuardar_NoReencriptaSiYaEsBCrypt() {
        Usuario u = new Usuario();
        u.setPassword("$2a$10$EXAMPLEPASSWORDHASH");

        when(usuarioRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Usuario result = usuarioService.guardar(u);

        assertEquals("$2a$10$EXAMPLEPASSWORDHASH", result.getPassword());
        verify(passwordEncoder, never()).encode(anyString());
    }

    // ============================================================
    // TEST: guardarSinEncriptarPassword()
    // ============================================================
    @Test
    void testGuardarSinEncriptarPassword() {
        Usuario u = new Usuario();
        u.setNombre("Fabian");

        when(usuarioRepository.save(any())).thenReturn(u);

        Usuario result = usuarioService.guardarSinEncriptarPassword(u);

        assertEquals("Fabian", result.getNombre());
        verify(usuarioRepository, times(1)).save(u);
    }

    // ============================================================
    // TEST: obtenerUsuarioActual()
    // ============================================================
    @Test
    void testObtenerUsuarioActual() {
        // Mock SecurityContextHolder
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("user@mail.com");
        when(auth.isAuthenticated()).thenReturn(true);

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(auth);

        SecurityContextHolder.setContext(context);

        Usuario u = new Usuario();
        u.setEmail("user@mail.com");

        when(usuarioRepository.findByEmail("user@mail.com"))
                .thenReturn(Optional.of(u));

        Usuario result = usuarioService.obtenerUsuarioActual();

        assertEquals("user@mail.com", result.getEmail());
    }
}
