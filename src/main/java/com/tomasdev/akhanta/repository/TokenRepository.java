package com.tomasdev.akhanta.repository;

import com.tomasdev.akhanta.model.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository extends MongoRepository<Token, String> {

    @Query("{'userId': ?0, 'expired': false, 'revoked': false}")
    List<Token> findActiveTokensByUserId(String userId);

    Token findTokenByToken(String token);

    void deleteByToken(String token);
}
