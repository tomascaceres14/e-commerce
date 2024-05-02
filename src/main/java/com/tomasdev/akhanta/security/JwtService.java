package com.tomasdev.akhanta.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.tomasdev.akhanta.exceptions.ResourceNotFoundException;
import com.tomasdev.akhanta.exceptions.UnauthorizedException;
import com.tomasdev.akhanta.model.Token;
import com.tomasdev.akhanta.model.User;
import com.tomasdev.akhanta.service.dto.TokenUserQuery;
import com.tomasdev.akhanta.model.dto.UserDTO;
import com.tomasdev.akhanta.repository.TokenRepository;
import com.tomasdev.akhanta.repository.UserRepository;
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

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtService {
    private final UserRepository userRepository;

    private final TokenRepository repository;
    private final ModelMapper mapper;
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshTokenExpiration;
    private final MongoTemplate mongoTemplate;

    public String extractUserEmail(String jwt) {
        return JWT.decode(jwt).getClaim("email").asString();
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

        return repository.save(new Token(null, token, new ObjectId(userDTO.getUserId()), false, false));
    }

    public Optional<Authentication> authorizeToken(String jwt) throws AuthenticationException {
        log.info("Authorizing token {}", jwt);

        // valida firma y expiración
        JWT.require(Algorithm.HMAC256(secretKey)).build().verify(jwt);

        String userEmail = extractUserEmail(jwt);

        // si el correo es nulo y el contexto tiene algo retorno nada
        if (userEmail == null || SecurityContextHolder.getContext().getAuthentication() != null) {
            throw new UnauthorizedException("Autorización rechazada.");
        }

        TokenUserQuery token = findByTokenWithUser(jwt);
        TokenUserQuery.UserInToken user = token.getUser();

        log.info("token {}", token);
        log.info("user {} {}", user.getRole(), user.getEmail());

        // valida si el token no expiro y no esta revocado
        if ((token.isExpired() || token.isRevoked())) {
            throw new UnauthorizedException("Token no válido. Inicie sesión nuevamente.");
        }

        HashSet<SimpleGrantedAuthority> rolesAndAuthorities = new HashSet<>();
        rolesAndAuthorities.add(new SimpleGrantedAuthority(STR."ROLE_\{user.getRole()}")); //rol

        return Optional.of(new UsernamePasswordAuthenticationToken(user, jwt, rolesAndAuthorities));
    }

    public TokenUserQuery findByTokenWithUser(String token) {
        LookupOperation lookupOperation = LookupOperation.newLookup()
                .from("users") // Nombre de la colección de usuarios
                .localField("userId")
                .foreignField("_id")
                .as("user");

        Aggregation aggregation = newAggregation(
                match(Criteria.where("token").is(token)),
                lookupOperation,
                unwind("user")
        );

        ProjectionOperation projectStage = Aggregation.project()
                .and("token").as("token")
                .and("user.username").as("username")
                .and("user.email").as("email")
                .and("expired").as("expired")
                .and("revoked").as("revoked");

        AggregationResults<TokenUserQuery> results = mongoTemplate.aggregate(aggregation, "tokens", TokenUserQuery.class);

        if (results.getMappedResults().isEmpty()) {
            throw new ResourceNotFoundException("Token no existente."); // No se encontraron resultados
        }

        return results.getMappedResults().getFirst(); // Devuelve el primer resultado
    }

    public void deleteToken(String jwt) {
        Token token = repository.findTokenByToken(jwt);

        if (token == null) {
            throw new ResourceNotFoundException("Token no existente.");
        }

        token.setRevoked(true);
        token.setExpired(true);

        repository.save(token);
        SecurityContextHolder.clearContext();
    }

}