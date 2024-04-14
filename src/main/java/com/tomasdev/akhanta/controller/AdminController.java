package com.tomasdev.akhanta.controller;

import com.tomasdev.akhanta.model.Activity;
import com.tomasdev.akhanta.model.Article;
import com.tomasdev.akhanta.model.Associate;
import com.tomasdev.akhanta.service.impl.ActivityServiceImpl;
import com.tomasdev.akhanta.service.impl.ArticleServiceImpl;
import com.tomasdev.akhanta.service.impl.AssociateServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private ArticleServiceImpl articleService;
    private ActivityServiceImpl activityService;
    private AssociateServiceImpl associateService;

    @PostMapping("/articles")
    ResponseEntity<Article> saveArticle(@Valid @RequestPart Article article, @RequestPart MultipartFile image) {
        return new ResponseEntity<>(articleService.saveWithImage(article, image), HttpStatus.CREATED);
    }

    @PutMapping("/articles/{id}")
    ResponseEntity<Article> updateArticleById(@PathVariable String id, @RequestPart Article article, @RequestPart MultipartFile image) {
        return ResponseEntity.status(HttpStatus.CREATED).body(articleService.updateWithImage(id, article, image));
    }

    @PostMapping("/associates")
    ResponseEntity<Associate> saveAssociate(@Valid @RequestPart Associate associate, @RequestPart MultipartFile profile, @RequestPart MultipartFile banner) {
        return new ResponseEntity<>(associateService.saveWithImages(associate, profile, banner), HttpStatus.CREATED);
    }

    @PutMapping("/associates/{id}")
    ResponseEntity<Associate> updateAssociateById(@PathVariable String id, @RequestPart Associate associate, @RequestPart MultipartFile profile, @RequestPart MultipartFile banner) {
        return new ResponseEntity<>(associateService.updateWithImages(id, associate, profile, banner), HttpStatus.CREATED);
    }

    @PostMapping("/activities")
    ResponseEntity<Activity> saveActivity(@Valid @RequestBody Activity activity) {
        return new ResponseEntity<>(activityService.save(activity), HttpStatus.CREATED);
    }




}
