package com.tomasdev.akhanta.user;

import com.tomasdev.akhanta.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String>, PagingAndSortingRepository<User, String> {

    Optional<User> findByEmail(String email);

}
