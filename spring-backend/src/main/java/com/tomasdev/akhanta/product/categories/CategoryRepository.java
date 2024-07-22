package com.tomasdev.akhanta.product.categories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
    @Query("{'node': ?0}")
    Optional<Category> findCategoryByNode(String node);
}
