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
public class JwtService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.expiration}")
    private long refreshTokenExpiration;

    public String extractUsername(String jwt) {
        return JWT.decode(jwt).getClaim("email").asString();
    }

    public boolean isTokenValid(String token, User user) {
        final String username = extractUsername(token);
        return username.equals(user.getEmail()) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return JWT.decode(token).getExpiresAt().before(new Date());
    }

    public Token generateAccessToken(User user) {
        return buildToken(mapper.map(user, UserDTO.class), jwtExpiration);
    }

    public Token generateRefreshToken(User user) {
        return buildToken(mapper.map(user, UserDTO.class), refreshTokenExpiration);
    }

    public Optional<Authentication> authorizeToken(String jwt) throws AuthenticationException {

        Optional<Authentication> auth = Optional.empty();

        // valida firma y expiración
        JWT.require(Algorithm.HMAC256(secretKey)).build().verify(jwt);

        String userEmail = extractUsername(jwt);

        // si el correo es nulo y el contexto esta vacío retorno nada)
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

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

    public Token buildToken(UserDTO userDTO, long expirationTime) {

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        String token = JWT.create()
                .withClaim("userId", userDTO.getUserId())
                .withClaim("lastname", userDTO.getLastName())
                .withClaim("numberCellPhone", String.valueOf(userDTO.getCellphone_number()))
                .withClaim("email", userDTO.getEmail())
                .withClaim("role", userDTO.getRole())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(algorithm);

        Token jwt = new Token(null, token, false, false);
        userDTO.getTokensList().add(jwt);
        userRepository.save(mapper.map(userDTO, User.class));

        return jwt;
    }

    public void deleteToken(String jwt) {

        User user = userRepository.findByEmail(extractUsername(jwt)).orElseThrow();

        user.getToken(jwt).get().setExpired(true);
        user.getToken(jwt).get().setRevoked(true);

        userRepository.save(user);

        SecurityContextHolder.clearContext();
    }

}