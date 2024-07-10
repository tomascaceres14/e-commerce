package com.tomasdev.akhanta.users.customer;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String>, PagingAndSortingRepository<Customer, String> {

    Optional<Customer> findByEmail(String email);

    @Update("{ $set : { 'status': ?1 } }")
    Integer findAndUpdateStatusByCustomerId(String email, Integer status);

}
