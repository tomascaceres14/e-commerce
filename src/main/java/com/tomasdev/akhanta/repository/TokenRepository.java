package com.tomasdev.akhanta.repository;

import com.tomasdev.akhanta.model.Token;
import com.tomasdev.akhanta.model.dto.TokenValidationDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository extends MongoRepository<Token, String> {

    @Query("{'userId': ?0, 'expired': false, 'revoked': false}")
    List<Token> findActiveTokensByUserId(String userId);

    @Query("{$match: {'token': ?0, 'expired': false, 'revoked': false}}, "
            + "{$lookup: {from: 'users', localField: 'userId', foreignField: '_id', as: 'user'}}, "
            + "{$unwind: '$user'}, "
            + "{$project: {'token': 1,'user': 1, 'expired': 1, 'revoked': 1}}")
    TokenValidationDTO findActiveTokenWithUserByToken(String token);
}
