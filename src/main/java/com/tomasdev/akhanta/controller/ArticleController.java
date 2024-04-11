package com.tomasdev.akhanta.controller;

import com.tomasdev.akhanta.model.Article;
import com.tomasdev.akhanta.model.Associate;
import com.tomasdev.akhanta.service.impl.ArticleServiceImpl;
import com.tomasdev.akhanta.service.impl.AmazonS3ServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping("/home/posts")
public class ArticleController {

    private ArticleServiceImpl service;
    private AmazonS3ServiceImpl s3Service;

    @GetMapping("")
    ResponseEntity<Page<Article>> findAll( @RequestParam(required = false, defaultValue = "0") Integer page) {
        return new ResponseEntity<>(service.findAll(page), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<Article> findById(@PathVariable String id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PostMapping("")
    ResponseEntity<Article> save(@Valid @RequestPart Article article, @RequestPart MultipartFile image) {
        return new ResponseEntity<>(service.saveWithImage(article, image), HttpStatus.CREATED);
    }
}
