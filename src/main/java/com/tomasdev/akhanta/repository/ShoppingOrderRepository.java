package com.tomasdev.akhanta.repository;

import com.tomasdev.akhanta.model.ShoppingOrder;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingOrderRepository extends MongoRepository<ShoppingOrder, String>, PagingAndSortingRepository<ShoppingOrder, String> {
}
