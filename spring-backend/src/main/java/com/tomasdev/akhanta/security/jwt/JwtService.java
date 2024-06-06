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

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtService {
    private final TokenRepository repository;
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

    public Optional<Authentication> authorizeToken(String jwt) throws AuthenticationException {
        log.info("Authorizing token {}", jwt);

        // valida firma y expiración
        JWT.require(Algorithm.HMAC256(secretKey)).build().verify(jwt);
/*
    Validacion innecesaria. userEmail NUNCA va a ser nulo si el token lo genero yo. Y si por algun
    motivo el token se edita manualmente y envia sin correo, tampoco llega acá. Crashea al validar
    la firma en linea anterior.
    El contexto nunca se usa ya que la autorizacion pasa por JWT.

        String userEmail = extractUserEmail(jwt);

        // si el correo es nulo o el contexto tiene algo retorno nada
        if (userEmail == null || SecurityContextHolder.getContext().getAuthentication() != null) {
            throw new UnauthorizedException("Autorización rechazada.");
        }
*/
        // Innecesario. Info del usuario ya esta en el token.
        TokenUserQuery token = findByTokenWithUser(jwt);
        // TODO reemplazar user con Hashmap con correo y rol.
        TokenUserQuery.UserInToken user = token.getUser();

        // TODO cambiar esta validacion. Solo debe validar si existe en tabla 'blacklist' de H2
        // valida si el token no expiro y no esta revocado
        if ((token.isExpired() || token.isRevoked())) {
            throw new UnauthorizedException("Token expirado/revocado.");
        }

        HashSet<SimpleGrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(STR."ROLE_\{user.getRole()}")); //rol

        // Quitar optional
        return Optional.of(new UsernamePasswordAuthenticationToken(user, jwt, roles));
    }

    // TODO borrar al pingo
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

    // TODO cambiar deleteToken por revokeToken. Cambiar lógica. Debe agregar el token a la tabla de blacklist.
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