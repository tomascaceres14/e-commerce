package com.tomasdev.akhanta.controller;

import com.tomasdev.akhanta.model.Article;
import com.tomasdev.akhanta.service.impl.ArticleServiceImpl;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ArticleController {

    private ArticleServiceImpl service;

    @GetMapping("")
    ResponseEntity<Page<Article>> findAll(@RequestParam Integer page) {
        return new ResponseEntity<>(service.findAll(page), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<Article> findById(@PathVariable String id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PostMapping("")
    ResponseEntity<Article> save(@Valid @RequestBody Article article) {
        return new ResponseEntity<>(service.save(article), HttpStatus.CREATED);
    }
}
