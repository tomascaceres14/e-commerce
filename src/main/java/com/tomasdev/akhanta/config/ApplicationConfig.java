package com.tomasdev.akhanta.config;

import com.tomasdev.akhanta.security.JwtAuthFilter;
import com.tomasdev.akhanta.security.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

/**
 * Clase de configuración para la creación de Beans a utilizar
 */
@Configuration
public class ApplicationConfig {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final HandlerExceptionResolver resolver;

    public ApplicationConfig(JwtAuthenticationProvider jwtAuthenticationProvider, @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
        this.resolver = resolver;
    }

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
        return new JwtAuthFilter(jwtAuthenticationProvider, resolver);
    }
}