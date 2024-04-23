package com.tomasdev.akhanta.controller;

import com.tomasdev.akhanta.model.Article;
import com.tomasdev.akhanta.model.Associate;
import com.tomasdev.akhanta.service.impl.ArticleServiceImpl;
import com.tomasdev.akhanta.service.impl.AssociateServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
@AllArgsConstructor
public class HomeController {

    private ArticleServiceImpl articleService;
    private AssociateServiceImpl associateService;

    @GetMapping("/articles")
    public ResponseEntity<Page<Article>> findAllArticles(@RequestParam(required = false, defaultValue = "0") Integer page) {
        return new ResponseEntity<>(articleService.findAllArticles(page), HttpStatus.OK);
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<Article> findArticleById(@PathVariable String id) {
        return new ResponseEntity<>(articleService.findArticleById(id), HttpStatus.OK);
    }

    @GetMapping("/associates")
    public ResponseEntity<Page<Associate>> findAllAssociates(@RequestParam(required = false, defaultValue = "0") Integer page) {
        return new ResponseEntity<>(associateService.findAllAssociates(page), HttpStatus.OK);
    }


    @GetMapping("/associates/{id}")
    ResponseEntity<Associate> findAssociateById(@PathVariable String id) {
        return new ResponseEntity<>(associateService.findAssociateById(id), HttpStatus.OK);
    }

}
