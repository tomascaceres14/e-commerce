package com.tomasdev.akhanta.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.tomasdev.akhanta.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Clase encargada de la creacion y validacion de jwt para el inicio de sesion de un Usuario
 */
@Component
public class JwtAuthenticationProvider {

    /**
     * Llave para cifrar el jwt
     */
    @Value("${jwt.secret.key}")
    private String secretKey;

    /**
     * Lista blanca con los jwt creados
     */
    private HashMap<String, UserDTO> listToken = new HashMap<>();


    /**
     * Crea un nuevo jwt en base al cliente recibido por parametro y lo agrega a la lista blanca
     * @param customerJwt Cliente a utilizar en la creacion del jwt
     * @return Jwt creado
     */
    public String createToken(UserDTO customerJwt) {

        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000); // 1 hora en milisegundos

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        String tokenCreated = JWT.create()
                .withClaim("userId", customerJwt.getUserId())
                .withClaim("lastname", customerJwt.getLastName())
                .withClaim("numberCellPhone", String.valueOf(customerJwt.getCellphone_number()))
                .withClaim("email", customerJwt.getEmail())
                .withClaim("role", customerJwt.getRole())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .sign(algorithm);

        listToken.put(tokenCreated, customerJwt);
        return tokenCreated;
    }


    /**
     * Valida si el token es valido y retorna una sesión del usuario
     * @param token Token a validar
     * @return Sesion del usuario
     * @throws CredentialsExpiredException Si el token ya expiró
     * @throws BadCredentialsException Si el token no existe en la lista blanca
     */
    public Authentication validateToken(String token) throws AuthenticationException {

        //verifica el token como su firma y expiración, lanza una excepcion si algo falla
        JWT.require(Algorithm.HMAC256(secretKey)).build().verify(token);

        UserDTO exists = listToken.get(token);
        if (exists == null) {
            throw new BadCredentialsException("Inicie sesión e intente nuevamente.");
        }

        HashSet<SimpleGrantedAuthority> rolesAndAuthorities = new HashSet<>();
        rolesAndAuthorities.add(new SimpleGrantedAuthority(STR."ROLE_\{exists.getRole()}")); //rol

        return new UsernamePasswordAuthenticationToken(exists, token, rolesAndAuthorities);
    }

    public void deleteToken(String jwt) {
        listToken.remove(jwt);
    }

}