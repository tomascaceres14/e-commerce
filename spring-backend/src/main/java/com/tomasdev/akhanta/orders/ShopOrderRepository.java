package com.tomasdev.akhanta.orders;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopOrderRepository extends MongoRepository<ShopOrder, String>, PagingAndSortingRepository<ShopOrder, String> {

    @Query("{customerId: {'$regex':?0,'$options':'i'}, shopId: {'$regex':?1,'$options':'i'}}")
    Page<ShopOrder> findAllFiltered(String customerId, String shopId, Pageable pageable);
}
