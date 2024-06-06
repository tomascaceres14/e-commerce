package com.tomasdev.akhanta.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.tomasdev.akhanta.exceptions.ResourceNotFoundException;
import com.tomasdev.akhanta.exceptions.UnauthorizedException;
import com.tomasdev.akhanta.user.User;
import com.tomasdev.akhanta.user.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.*;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtService {
    private final TokenRepository repository;
    private final TokenBlacklistRepository blacklistRepository;
    private final ModelMapper mapper;
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshTokenExpiration;
    private final MongoTemplate mongoTemplate;

    public static String extractUserEmail(String jwt) {
        return JWT.decode(jwt).getClaim("email").asString();
    }
    public static String extractClaim(String jwt, String claim) {
        return JWT.decode(jwt).getClaim(claim).asString();
    }

    public boolean isTokenValid(String token, User user) {
        final String username = extractUserEmail(token);
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
    // TODO cambiar parámetro userDTO por hashmap con información específica.
    // Es mas engorroso pero permite flexibilidad para crear diferentes tokens con diferentes claims.
    public Token buildToken(UserDTO userDTO, long expirationTime) {

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        String token = JWT.create()
                .withClaim("userId", userDTO.getUserId())
                .withClaim("lastname", userDTO.getLastName())
                .withClaim("numberCellPhone", String.valueOf(userDTO.getCellphone_number()))
                .withClaim("email", userDTO.getEmail())
                .withClaim("role", userDTO.getRole())
                .withClaim("cart", userDTO.getCartId().toString())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(algorithm);

        return repository.save(new Token(null, token, new ObjectId(userDTO.getUserId()), false, false));
    }

    public Authentication authorizeToken(String jwt) throws AuthenticationException {
        log.info("Authorizing token {}", jwt);

        // valida firma y expiración
        JWT.require(Algorithm.HMAC256(secretKey)).build().verify(jwt);

        Map<String, String> user = new HashMap<>();
        user.put("email", extractClaim(jwt, "email"));
        user.put("role", extractClaim(jwt, "role"));

        if (blacklistRepository.existsByToken(jwt)) {
            throw new UnauthorizedException("Token expirado/revocado.");
        }

        HashSet<SimpleGrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(STR."ROLE_\{user.get("role")}")); //rol

        return new UsernamePasswordAuthenticationToken(user, jwt, roles);
    }

    public void revokeToken(String jwt) {
        blacklistRepository.save(new TokenBlacklist(jwt));
        SecurityContextHolder.clearContext();
    }
}