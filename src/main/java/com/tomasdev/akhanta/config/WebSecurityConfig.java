package com.tomasdev.akhanta.config;

import com.tomasdev.akhanta.exceptions.AccessDeniedHandlerException;
import com.tomasdev.akhanta.exceptions.RestAuthenticationEntryPoint;
import com.tomasdev.akhanta.security.JwtAuthFilter;
import com.tomasdev.akhanta.security.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Clase que configura lo relacionado a las peticiones HTTP
 */
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    AccessDeniedHandlerException accessDeniedHandlerException;
    RestAuthenticationEntryPoint authEntryPoint;

    /**
     * Configura la seguridad de las peticiones HTTP
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(withDefaults())
                .exceptionHandling((exception) -> exception.accessDeniedHandler(accessDeniedHandlerException)
                                .authenticationEntryPoint(authEntryPoint))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests ->
                        requests
                                .requestMatchers("/auth/**", "/home/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/").hasAnyRole(Roles.CUSTOMER, Roles.ADMIN)
                                .requestMatchers("/admin/**").hasRole(Roles.ADMIN)
                                .anyRequest().authenticated()

                );

        return http.build();
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(CorsConfiguration.ALL));
        configuration.setAllowedMethods(List.of(CorsConfiguration.ALL));
        configuration.setAllowedHeaders(List.of(CorsConfiguration.ALL));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}