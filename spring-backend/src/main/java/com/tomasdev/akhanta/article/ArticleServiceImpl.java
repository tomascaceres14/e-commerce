package com.tomasdev.akhanta.article;

import com.tomasdev.akhanta.exceptions.ResourceNotFoundException;
import com.tomasdev.akhanta.images.AmazonS3ServiceImpl;
import com.tomasdev.akhanta.utils.StringUtils;
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
    public Page<Article> findAllArticles(int page) {
        PageRequest pageable = PageRequest.of(page, 10);
        return repository.findAll(pageable);
    }

    @Override
    public Article saveArticle(ArticleRequestDTO articleDTO, MultipartFile image) {
        Article article = mapper.map(articleDTO, Article.class);

        article.setImage_url(s3Service.upload(image, s3Folder));
        article.setSeTitle(StringUtils.normalizeToSearch(article.getTitle()));
        article.setCreation_date(new Date());
        article = repository.save(article);

        log.info("[ Creating new article id: {} ]", article.getArticleId());
        return article;
    }

    @Override
    public Article findArticleById(String id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Articulo"));
    }

    @Override
    public Article updateArticleById(String id, ArticleRequestDTO articleDTO, MultipartFile image) {
        Article articleDB = findArticleById(id);

        if (image != null) {
            String imageName =  s3Service.getImageKeyFromUrl(articleDB.getImage_url());
            articleDB.setImage_url(s3Service.update(image, s3Folder, imageName));
        }

        if (articleDTO != null) {
            mapper.map(articleDTO, articleDB);
            articleDB.setArticleId(id);
        }

        log.info("[ Updating article id: {} ]", id);
        return repository.save(articleDB);
    }

    @Override
    public void deleteArticleById(String id) {
        Article article = findArticleById(id);
        s3Service.delete(s3Folder, s3Service.getImageKeyFromUrl(article.getImage_url()));

        log.info("[ Deleting article id: {}  ]", id);
        repository.deleteById(id);
    }
}