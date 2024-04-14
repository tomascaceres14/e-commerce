package com.tomasdev.akhanta.security;

import com.tomasdev.akhanta.exceptions.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    /**
     * Lista blanca de URIs
     */
    private List<String> urlsToSkip = List.of("/", "/home/*", "/auth");


    /**
     * Verifica si a la URI no se le debe aplicar el filtro
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return urlsToSkip.stream().anyMatch(url -> request.getRequestURI().contains(url));
    }


    /**
     * Valida si la petición contiene la cabecera de authorization con el bearer token
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null) {
            throw new UnauthorizedException();
        }

        String[] authElements = header.split(" ");

        if (authElements.length != 2 || !"Bearer".equals(authElements[0])) {
            throw new UnauthorizedException();
        }

        try {
            Authentication auth = jwtAuthenticationProvider.validateToken(authElements[1]);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (RuntimeException e) {
            SecurityContextHolder.clearContext();
            throw new RuntimeException(e);
        }

        filterChain.doFilter(request, response);
    }
}