package com.tomasdev.akhanta.repository;

import com.tomasdev.akhanta.model.CalendarDay;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarRepository extends MongoRepository<CalendarDay, String>, PagingAndSortingRepository<CalendarDay, String> {
}
