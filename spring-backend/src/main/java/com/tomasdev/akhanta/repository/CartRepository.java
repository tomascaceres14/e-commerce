package com.tomasdev.akhanta.repository;

import com.tomasdev.akhanta.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends MongoRepository<Cart, String>, PagingAndSortingRepository<Cart, String> {
}
