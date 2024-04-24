package com.tomasdev.akhanta.repository;

import com.tomasdev.akhanta.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String>, PagingAndSortingRepository<Product, String> {
}
