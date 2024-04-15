package com.tomasdev.akhanta.service;

import com.tomasdev.akhanta.model.Article;
import org.springframework.web.multipart.MultipartFile;

public interface ArticleService extends iService<Article> {
    Article saveWithImage(Article req, MultipartFile image);

    Article updateWithImage(String id, Article article, MultipartFile image);
}
