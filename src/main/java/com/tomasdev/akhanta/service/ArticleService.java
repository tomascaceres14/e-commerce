package com.tomasdev.akhanta.service;

import com.tomasdev.akhanta.model.Article;
import com.tomasdev.akhanta.model.dto.ArticleRequestDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ArticleService extends iService<Article> {
    Article saveWithImage(ArticleRequestDTO articleDTO, MultipartFile image);

    Article updateWithImage(String id, ArticleRequestDTO articleDTO, MultipartFile image);
}
