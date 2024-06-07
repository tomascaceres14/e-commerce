package com.tomasdev.akhanta.security.jwt;

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
import java.util.Objects;

/**
 * Filtro que valida si la peticion tiene la cabezera de Autorizacion
 */
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final HandlerExceptionResolver resolver;
    private final List<String> urlsToSkip = List.of("/api/v1/auth", "/api/v1/home", "/favicon.ico", "/h2-console");

    public JwtAuthFilter(JwtService jwtService, @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.jwtService = jwtService;
        this.resolver = resolver;
    }


    /**
     * Verifica si a la URI no se le debe aplicar el filtro
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        if (request.getRequestURI().equals("/")) return true;
        return urlsToSkip.stream().anyMatch(url -> request.getRequestURI().contains(url));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, UnauthorizedException {

        Authentication auth;
        String jwt = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (jwt == null || JwtService.extractClaim(jwt, "isRefresh").equals("true")) {
            resolver.resolveException(request, response, null, new UnauthorizedException("Cabecera no válida. Inicie sesión e intente nuevamente."));
            return;
        }

        try {
            auth = jwtService.authorizeToken(jwt);
        }catch (Exception e) {
            resolver.resolveException(request, response, null, new UnauthorizedException(e.getMessage()));
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }
}