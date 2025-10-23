package com.example.demo_app_vehiculos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public WebSecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // Encriptador de contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Proveedor de autenticación
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // Configuración de rutas y login
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Páginas públicas
                .requestMatchers(
                        "/", 
                        "/usuarios/registro", 
                        "/usuarios", 
                        "/login", 
                        "/css/**", 
                        "/js/**", 
                        "/img/**",
                        "/autos", 
                        "/vehiculos", 
                        "/autosusados", 
                        "/bicicletas", 
                        "/balanzas", 
                        "/soportetecnico"
                ).permitAll()

                // Solo ADMIN puede eliminar usuarios
                .requestMatchers("/usuarios/eliminar/**").hasRole("ADMIN")

                // Todo lo demás requiere login
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")             // tu login.html
                .defaultSuccessUrl("/", true)    // redirige al home tras login
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
            );

        return http.build();
    }
}
