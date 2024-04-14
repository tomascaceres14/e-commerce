package com.tomasdev.akhanta.service.impl;

import com.tomasdev.akhanta.exceptions.ResourceNotFoundException;
import com.tomasdev.akhanta.model.Article;
import com.tomasdev.akhanta.repository.ArticleRepository;
import com.tomasdev.akhanta.service.ArticleService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private ArticleRepository repository;
    private AmazonS3ServiceImpl s3Service;
    private ModelMapper mapper;

    @Override
    public Article save(Article req) {
        return repository.save(req);
    }

    @Override
    public Article saveWithImage(Article req, MultipartFile image) {
        req.setImage_url(s3Service.upload(image, "articulos"));
        return repository.save(req);
    }

    @Override
    public Article updateWithImage(String id, Article article, MultipartFile image) {
        return null;
    }

    @Override
    public Page<Article> findAll(int page) {
        PageRequest pageable = PageRequest.of(page, 10);
        return repository.findAll(pageable);
    }

    @Override
    public Article findById(String id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Articulo"));
    }

    @Override
    public Article updateById(String id, Article req) {
        Article articleDB = findById(id);
        mapper.map(req, articleDB);
        articleDB.setId(id);
        return repository.save(articleDB);
    }

    @Override
    public void deleteById(String id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Articulo");
        }
        repository.deleteById(id);
    }
}