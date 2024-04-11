package com.tomasdev.akhanta.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.tomasdev.akhanta.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

@Component
public class JwtAuthenticationProvider {

    @Value("${jwt.secret.key}")
    private String secretKey;

    private HashMap<String, UserDTO> tokensWhitelist = new HashMap<>();

    public String createToken(UserDTO customerJwt) {

        Date now = new Date();
        Date validity = new Date(now.getTime() + 604800016); // 1 semana en milisegundos

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        String tokenCreated = JWT.create()
                .withClaim("cardId", customerJwt.getId())
                .withClaim("firstName", customerJwt.getFirstName())
                .withClaim("lastName", customerJwt.getLastName())
                .withClaim("numberCellPhone", String.valueOf(customerJwt.getCellphone_number()))
                .withClaim("email", customerJwt.getEmail())
                .withClaim("role", customerJwt.getRole())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .sign(algorithm);

        tokensWhitelist.put(tokenCreated, customerJwt);
        return tokenCreated;
    }

    public Authentication validateToken(String token) throws AuthenticationException {

        //verifica el token como su firma y expiración, lanza una excepcion si algo falla
        JWT.require(Algorithm.HMAC256(secretKey)).build().verify(token);

        UserDTO exists = tokensWhitelist.get(token);
        if (exists == null) {
            throw new BadCredentialsException("Usuario no registrado.");
        }

        HashSet<SimpleGrantedAuthority> rolesAndAuthorities = new HashSet<>();
        rolesAndAuthorities.add(new SimpleGrantedAuthority("ROLE_"+exists.getRole())); //rol


        return new UsernamePasswordAuthenticationToken(exists, token, rolesAndAuthorities);
    }

    public String deleteToken(String jwt) {

        if (!tokensWhitelist.containsKey(jwt)) {
            return "No existe el token.";
        }

        tokensWhitelist.remove(jwt);
        return "Sesión cerrada exitosamente.";
    }

}
