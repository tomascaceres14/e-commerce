package com.tomasdev.akhanta.article;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/home/articles")
@AllArgsConstructor
public class ArticleController {

    private ArticleService articleService;

    @GetMapping
    public ResponseEntity<Page<Article>> findAllArticles(@RequestParam(required = false, defaultValue = "0") Integer page) {
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        return ResponseEntity.ok().body(articleService.findAllArticles(page));
    }

    @GetMapping("{id}")
    public ResponseEntity<Article> findArticleById(@PathVariable String id) {
        return ResponseEntity.ok().body(articleService.findArticleById(id));
    }
}
