package com.tomasdev.akhanta.users.shop;

import org.springframework.data.mongodb.repository.ExistsQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopRepository extends MongoRepository<Shop, String>, PagingAndSortingRepository<Shop, String> {

    Optional<Shop> findByEmail(String email);
    Optional<Shop> findBySeName(String seName);

    @ExistsQuery("{ 'email': ?0}")
    boolean existByEmail(String email);

}
