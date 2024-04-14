package com.tomasdev.akhanta.controller;

import com.tomasdev.akhanta.model.Activity;
import com.tomasdev.akhanta.model.Article;
import com.tomasdev.akhanta.model.Associate;
import com.tomasdev.akhanta.service.impl.ActivityServiceImpl;
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
    private ActivityServiceImpl activityService;
    private AssociateServiceImpl associateService;

    @GetMapping("/articles")
    ResponseEntity<Page<Article>> findAllArticles(@RequestParam(required = false, defaultValue = "0") Integer page) {
        return new ResponseEntity<>(articleService.findAll(page), HttpStatus.OK);
    }

    @GetMapping("/articles/{id}")
    ResponseEntity<Article> findArticleById(@PathVariable String id) {
        return new ResponseEntity<>(articleService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/activities")
    ResponseEntity<Page<Activity>> findAllActivities(@RequestParam(required = false, defaultValue = "0") Integer page) {
        return new ResponseEntity<>(activityService.findAll(page), HttpStatus.OK);
    }

    @GetMapping("/activities/{id}")
    ResponseEntity<Activity> findActivityById(@PathVariable String id) {
        return new ResponseEntity<>(activityService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/associates")
    ResponseEntity<Page<Associate>> findAll(@RequestParam(required = false, defaultValue = "0") Integer page) {
        return new ResponseEntity<>(associateService.findAll(page), HttpStatus.OK);
    }


    @GetMapping("/associates/{id}")
    ResponseEntity<Associate> findAssociateById(@PathVariable String id) {
        return new ResponseEntity<>(associateService.findById(id), HttpStatus.OK);
    }

}
