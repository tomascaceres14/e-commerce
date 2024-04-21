package com.tomasdev.akhanta.controller;

import com.tomasdev.akhanta.model.Activity;
import com.tomasdev.akhanta.model.Article;
import com.tomasdev.akhanta.model.Associate;
import com.tomasdev.akhanta.model.dto.ArticleRequestDTO;
import com.tomasdev.akhanta.model.dto.AssociateRequestDTO;
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
    ResponseEntity<Article> saveArticle(@Valid @RequestPart ArticleRequestDTO article,
                                        @RequestPart MultipartFile image) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(articleService.saveWithImage(article, image));
    }

    @PutMapping("/articles/{id}")
    ResponseEntity<Article> updateArticleById(@PathVariable String id,
                                              @RequestPart(required = false) ArticleRequestDTO article,
                                              @RequestPart(required = false) MultipartFile image) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(articleService.updateWithImage(id, article, image));
    }

    @DeleteMapping("/articles/{id}")
    ResponseEntity<String> deleteArticleById(@PathVariable String id) {
        articleService.deleteById(id);
        return ResponseEntity.ok(STR."Articulo id \{id} eliminado.");
    }

    @PostMapping("/associates")
    ResponseEntity<Associate> saveAssociate(@Valid @RequestPart AssociateRequestDTO associate,
                                            @RequestPart MultipartFile profile,
                                            @RequestPart MultipartFile banner) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(associateService.saveWithImages(associate, profile, banner));
    }

    @PutMapping("/associates/{id}")
    ResponseEntity<Associate> updateAssociateById(@PathVariable String id,
                                                  @RequestPart(required = false) AssociateRequestDTO associate,
                                                  @RequestPart(required = false) MultipartFile profile,
                                                  @RequestPart(required = false) MultipartFile banner) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(associateService.updateWithImages(id, associate, profile, banner));
    }

    @DeleteMapping("/associates/{id}")
    ResponseEntity<String> deleteAssociatesById(@PathVariable String id) {
        associateService.deleteById(id);
        return ResponseEntity.ok(STR."Asociado id \{id} eliminado.");
    }

    @PostMapping("/activities")
    ResponseEntity<Activity> saveActivity(@Valid @RequestBody Activity activity) {
        return new ResponseEntity<>(activityService.save(activity), HttpStatus.CREATED);
    }

}