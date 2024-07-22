package com.tomasdev.akhanta.associate;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociateRepository extends MongoRepository<Associate, String>, PagingAndSortingRepository<Associate, String> {
    Associate findByName(String name);
}
