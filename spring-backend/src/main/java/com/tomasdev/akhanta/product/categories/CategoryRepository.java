package com.tomasdev.akhanta.product.categories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {

    @Query("{'parentId': null}")
    List<Category> findAllParentCategories();
}
