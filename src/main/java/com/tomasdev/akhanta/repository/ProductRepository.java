package com.tomasdev.akhanta.repository;

import com.tomasdev.akhanta.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String>, PagingAndSortingRepository<Product, String> {

    @Query("{title: {'$regex':?0,'$options':'i'}, categoryId: ?1}")
    Page<Product> findAllProductsByName(String title, String categoryId, Pageable page);

}
