package com.tomasdev.akhanta.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String>, PagingAndSortingRepository<Product, String> {

    @Query("{title: {'$regex':?0,'$options':'i'}, categoryId: {'$regex':?1,'$options':'i'}}")
    Page<Product> filterAllProducts(String title, String categoryId, Pageable page);
}
