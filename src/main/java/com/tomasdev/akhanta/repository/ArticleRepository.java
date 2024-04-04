package com.tomasdev.akhanta.repository;

import com.tomasdev.akhanta.model.Article;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends MongoRepository<Article, String>, PagingAndSortingRepository<Article, String> {

}
