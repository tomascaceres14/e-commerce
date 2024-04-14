package com.tomasdev.akhanta.config;

import com.tomasdev.akhanta.security.JwtAuthFilter;
import com.tomasdev.akhanta.security.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Clase de configuración para la creación de Beans a utilizar
 */
@RequiredArgsConstructor
@Configuration
public class ApplicationConfig {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    /**
     * Bean de Password Encoder para inyeccion
     * @return Implemetación BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean de JwtAuthFilter para inyeccion
     * @return Implementación JwtAuthFilter
     */
    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter(jwtAuthenticationProvider);
    }

}