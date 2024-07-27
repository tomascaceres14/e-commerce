package com.tomasdev.akhanta.config;

import com.tomasdev.akhanta.exceptions.CustomAccessDeniedHandler;
import com.tomasdev.akhanta.exceptions.AuthEntryPoint;
import com.tomasdev.akhanta.security.jwt.JwtAuthFilter;
import com.tomasdev.akhanta.security.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Clase que configura lo relacionado a las peticiones HTTP
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthEntryPoint authEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    /**
     * Configura la seguridad de las peticiones HTTP
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception.accessDeniedHandler(accessDeniedHandler)
                                .authenticationEntryPoint(authEntryPoint))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests ->
                        requests
                                .requestMatchers("/test/**", "/api/v1/auth/**", "/api/v1/home/**", "/h2-console/**").permitAll()
                                .requestMatchers("/api/v1/admin/**").hasRole(Roles.ADMIN)
                                .requestMatchers("/api/v1/users/**").hasAnyRole(Roles.ADMIN, Roles.USER)
                                .requestMatchers("/api/v1/customers/**").hasAnyRole(Roles.ADMIN, Roles.USER)
                                .requestMatchers("/api/v1/orders/**").hasAnyRole(Roles.ADMIN, Roles.USER)
                                .requestMatchers("/api/v1/shops/**").hasAnyRole(Roles.ADMIN, Roles.OWNER)
                                .anyRequest().authenticated()
                )
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        return http.build();
    }
}