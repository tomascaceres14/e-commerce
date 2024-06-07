package com.tomasdev.akhanta.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.tomasdev.akhanta.exceptions.UnauthorizedException;
import com.tomasdev.akhanta.users.User;
import com.tomasdev.akhanta.users.customer.Customer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtService {
    private final TokenBlacklistRepository blacklistRepository;
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long accessTokenExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshTokenExpiration;

    public static String extractClaim(String jwt, String claim) {
        return JWT.decode(jwt.substring(7)).getClaim(claim).asString();
    }

    public String buildToken(Map<String, Object> claims, long expirationTime) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withPayload(claims)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(algorithm);
    }

    public String buildCustomerAccessToken(Customer customer) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("customerId", customer.getCustomerId());
        claims.put("username", customer.getUsername());
        claims.put("role", customer.getRole());
        claims.put("cartId", customer.getCartId());
        claims.put("isRefresh", "false");

        return  buildToken(claims, accessTokenExpiration);
    }

    public String buildRefreshToken(User customer) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("isRefresh", "true");
        claims.put("email", customer.getEmail());

        return buildToken(claims, refreshTokenExpiration);
    }

    public Authentication authorizeToken(String jwt) throws AuthenticationException {
        log.info("Authorizing token {}", jwt);

        // valida firma y expiración
        JWT.require(Algorithm.HMAC256(secretKey)).build().verify(jwt.substring(7));

        if (blacklistRepository.existsByToken(jwt)) {
            throw new UnauthorizedException("Token expirado/revocado.");
        }

        Map<String, String> user = new HashMap<>();
        user.put("email", extractClaim(jwt, "email"));
        user.put("role", extractClaim(jwt, "role"));

        HashSet<SimpleGrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(STR."ROLE_\{user.get("role")}")); //rol

        return new UsernamePasswordAuthenticationToken(user, jwt, roles);
    }

    public void revokeToken(String jwt) {
        blacklistRepository.save(new TokenBlacklist(null, jwt));
        SecurityContextHolder.clearContext();
    }
}