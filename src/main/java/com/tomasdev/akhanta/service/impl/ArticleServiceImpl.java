package com.tomasdev.akhanta.service.impl;

import com.tomasdev.akhanta.exceptions.ResourceNotFoundException;
import com.tomasdev.akhanta.model.Article;
import com.tomasdev.akhanta.model.dto.ArticleRequestDTO;
import com.tomasdev.akhanta.repository.ArticleRepository;
import com.tomasdev.akhanta.service.ArticleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Slf4j
@Service
@AllArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private ArticleRepository repository;
    private ModelMapper mapper;
    private AmazonS3ServiceImpl s3Service;
    private final String s3Folder = "articulos";

    @Override
    public Page<Article> findAll(int page) {
        PageRequest pageable = PageRequest.of(page, 10);
        return repository.findAll(pageable);
    }

    @Override
    public Article save(Article req) {
        return repository.save(req);
    }

    @Override
    public Article saveWithImage(ArticleRequestDTO req, MultipartFile image) {
        Article article = mapper.map(req, Article.class);

        article.setImage_url(s3Service.upload(image, s3Folder));

        log.info("[ Creating new article - {} ]", new Date());
        return repository.save(article);
    }

    @Override
    public Article findById(String id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Articulo"));
    }

    @Override
    public Article updateById(String id, Article req) {
        Article articleDB = findById(id);

        mapper.map(req, articleDB);
        articleDB.setArticleId(id);

        return repository.save(articleDB);
    }

    @Override
    public Article updateWithImage(String id, ArticleRequestDTO articleDTO, MultipartFile image) {
        Article articleDB = findById(id);

        if (image != null) {
            String imageName =  s3Service.getImageKeyFromUrl(articleDB.getImage_url());
            articleDB.setImage_url(s3Service.update(image, s3Folder, imageName));
        }

        if (!(articleDTO == null)) {
            mapper.map(articleDTO, articleDB);
            articleDB.setArticleId(id);
        }

        log.info("[ Updating article id: {} - {} ]", id, new Date());
        return repository.save(articleDB);
    }

    @Override
    public void deleteById(String id) {
        Article article = findById(id);
        s3Service.delete(s3Folder, s3Service.getImageKeyFromUrl(article.getImage_url()));

        log.info("[ Deleting article id: {} - {} ]", id, new Date());
        repository.deleteById(id);
    }
}