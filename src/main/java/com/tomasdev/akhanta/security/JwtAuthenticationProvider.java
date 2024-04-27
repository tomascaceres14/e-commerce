package com.tomasdev.akhanta.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.tomasdev.akhanta.model.Token;
import com.tomasdev.akhanta.model.User;
import com.tomasdev.akhanta.model.dto.UserDTO;
import com.tomasdev.akhanta.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

/**
 * Clase encargada de la creacion y validacion de jwt para el inicio de sesion de un Usuario
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider {

    @Value("${jwt.secret.key}")
    private String secretKey;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public String extractUsername(String jwt) {
        return JWT.decode(jwt).getClaim("email").asString();
    }

    private boolean isTokenExpired(String jwt) {
        return JWT.decode(jwt).getExpiresAt().before(new Date());
    }

    public Token createToken(UserDTO userDTO) {

        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000); // 1 hora en milisegundos

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        String token = JWT.create()
                .withClaim("userId", userDTO.getUserId())
                .withClaim("lastname", userDTO.getLastName())
                .withClaim("numberCellPhone", String.valueOf(userDTO.getCellphone_number()))
                .withClaim("email", userDTO.getEmail())
                .withClaim("role", userDTO.getRole())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .sign(algorithm);

        Token jwt = new Token(null, token, false, false);
        userDTO.getTokensList().add(jwt);
        userRepository.save(mapper.map(userDTO, User.class));

        return jwt;
    }

    public Optional<Authentication> validateToken(String jwt) throws AuthenticationException {

        Optional<Authentication> auth = Optional.empty();

        // valida firma y expiración
        JWT.require(Algorithm.HMAC256(secretKey)).build().verify(jwt);

        // extraigo correo de usuario por Claims de jwt
        String userEmail = extractUsername(jwt);

        // si el correo es nulo y el contexto esta vacío retorno nada)
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Busco el usuario
            User user = userRepository.findByEmail(userEmail).orElseThrow();

            // valida si el token no expiro y no esta revocado. Sino, devuelve false
            var isTokenValid = user.getToken(jwt)
                    .map(t -> !t.isExpired() && !t.isRevoked())
                    .orElse(false);

            // validamos y damos acceso
            if (userEmail.equals(user.getEmail()) && !isTokenExpired(jwt) && isTokenValid) {
                HashSet<SimpleGrantedAuthority> rolesAndAuthorities = new HashSet<>();
                rolesAndAuthorities.add(new SimpleGrantedAuthority(STR."ROLE_\{user.getRole()}")); //rol

                auth = Optional.of(new UsernamePasswordAuthenticationToken(user, jwt, rolesAndAuthorities));
            }
        }

        return auth;
    }

    public void deleteToken(String jwt) {

        User user = userRepository.findByEmail(extractUsername(jwt)).orElseThrow();

        user.getToken(jwt).get().setExpired(true);
        user.getToken(jwt).get().setRevoked(true);

        userRepository.save(user);

        SecurityContextHolder.clearContext();
    }

}