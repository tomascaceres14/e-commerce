package com.tomasdev.akhanta.repository;

import com.tomasdev.akhanta.model.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends MongoRepository<Activity, String>, PagingAndSortingRepository<Activity, String> {

}
