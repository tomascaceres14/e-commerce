package com.tomasdev.akhanta.security.jwt;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenBlacklistRepository extends CrudRepository<TokenBlacklist, Long> {
    boolean existsByToken(String token);

    TokenBlacklist findByToken(String token);
}
