package com.tomasdev.akhanta.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String>, PagingAndSortingRepository<User, String> {

    Optional<User> findByEmail(String email);

    @Update("{ $set : { 'status': ?1 } }")
    Integer findAndUpdateStatusById(String email, Integer status);

    @Update("{ $set : { 'role': ?1 } }")
    Integer findAndUpdateRoleById(String id, String role);

    @Update("{ $set : { 'shopId': ?1 } }")
    Integer findAndUpdateShopIdById(String id, String shopId);
}
