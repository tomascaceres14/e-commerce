package com.tomasdev.akhanta.repository;

import com.tomasdev.akhanta.model.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface JwtRepository extends MongoRepository<Token, String> {

    Optional<Token> findByToken(String token);

    void deleteByToken(String token);
}
