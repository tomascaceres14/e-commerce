package com.tomasdev.akhanta.article;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface ArticleService {
    Page<Article> findAllArticles(int page);
    Article saveArticle(ArticleRequestDTO articleDTO, MultipartFile image);
    Article findArticleById(String id);
    Article updateArticleById(String id, ArticleRequestDTO associateDTO, MultipartFile image);
    void deleteArticleById(String id);
}
