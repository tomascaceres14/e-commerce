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

/**
 * Filtro que valida si la peticion tiene la cabezera de Autorizacion
 */
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    /**
     * Lista blanca de URIs
     */
    private List<String> urlsToSkip = List.of("/auth", "/home");

    /**
     * Verifica si a la URI no se le debe aplicar el filtro
     * @return True la URI existe en la lista blanca, false de lo contrario
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        System.out.println(request.getRequestURI());
        return urlsToSkip.stream().anyMatch(url -> request.getRequestURI().contains(url));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, UnauthorizedException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null) {
            throw new UnauthorizedException("Error validando la sesión. Cabecera no incluida");
        }

        String[] authElements = header.split(" ");

        if (authElements.length != 2 || !"Bearer".equals(authElements[0])) {
            throw new UnauthorizedException("Mal formato del token.");
        }

        try {
            Authentication auth = jwtAuthenticationProvider.validateToken(authElements[1]);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (RuntimeException e) {
            SecurityContextHolder.clearContext();
            throw new RuntimeException(e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}