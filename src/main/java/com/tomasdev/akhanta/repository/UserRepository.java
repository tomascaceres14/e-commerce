package com.tomasdev.akhanta.repository;

import com.tomasdev.akhanta.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String>, PagingAndSortingRepository<User, String> {

    Optional<User> findByEmail(String email);
}
