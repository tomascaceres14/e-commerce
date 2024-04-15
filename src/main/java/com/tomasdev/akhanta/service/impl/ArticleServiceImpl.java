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
    public Article saveWithImage(Article req, MultipartFile image) {
        System.out.println("Hasta aca llegue");
        req.setImage_url(s3Service.upload(image, s3Folder));
        return repository.save(req);
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
    public Article updateWithImage(String id, Article req, MultipartFile image) {
        Article articleDB = findById(id);


        if (!(image == null)) {
            String imageName =  s3Service.getImageKeyFromUrl(articleDB.getImage_url());
            req.setImage_url(s3Service.update(image, s3Folder, imageName));
        }

        if (!(req == null)) {
            mapper.map(req, articleDB);
        }

        System.out.println(articleDB);
        return repository.save(articleDB);
    }

    @Override
    public void deleteById(String id) {
        Article article = findById(id);
        s3Service.delete(s3Folder, s3Service.getImageKeyFromUrl(article.getImage_url()));
        repository.deleteById(id);
    }
}