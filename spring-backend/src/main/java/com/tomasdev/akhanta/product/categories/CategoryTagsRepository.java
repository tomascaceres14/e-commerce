package com.tomasdev.akhanta.product.categories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryTagsRepository extends MongoRepository<CategoryTag, String> {

    @Query("{'parentCategory': null}")
    List<CategoryTag> findAllParentCategories();
}
