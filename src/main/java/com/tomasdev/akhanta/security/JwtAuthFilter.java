package com.tomasdev.akhanta.security;

import com.tomasdev.akhanta.exceptions.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.List;

/**
 * Filtro que valida si la peticion tiene la cabezera de Autorizacion
 */
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final HandlerExceptionResolver resolver;

    public JwtAuthFilter(JwtAuthenticationProvider jwtAuthenticationProvider, @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
        this.resolver = resolver;
    }

    /**
     * Lista blanca de URIs
     */
    private final List<String> urlsToSkip = List.of("/auth", "/home", "/favicon.ico");

    /**
     * Verifica si a la URI no se le debe aplicar el filtro
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        if (request.getRequestURI().equals("/")) return true;
        return urlsToSkip.stream().anyMatch(url -> request.getRequestURI().contains(url));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, UnauthorizedException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith("Bearer ")) {
            log.info("Inicie sesión e intente nuevamente.");
            resolver.resolveException(request, response, null, new UnauthorizedException("Inicie sesión e intente nuevamente."));
            return;
        }

        String[] authElements = header.split(" ");

        try {
            Authentication auth = jwtAuthenticationProvider.validateToken(authElements[1]);
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response);
        } catch (RuntimeException e) {
            log.error("{} - {}", e.getClass().getSimpleName(), e.getMessage());
            resolver.resolveException(request, response, null, e);
        }

    }
}