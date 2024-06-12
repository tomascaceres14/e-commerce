package com.tomasdev.akhanta.orders;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopOrderRepository extends MongoRepository<ShopOrder, String>, PagingAndSortingRepository<ShopOrder, String> {
}
