package com.tomasdev.akhanta.controller;

import com.tomasdev.akhanta.model.Article;
import com.tomasdev.akhanta.model.Associate;
import com.tomasdev.akhanta.model.Product;
import com.tomasdev.akhanta.service.ArticleService;
import com.tomasdev.akhanta.service.AssociateService;
import com.tomasdev.akhanta.service.ProductService;
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

    private ArticleService articleService;
    private AssociateService associateService;
    private ProductService productService;

    @GetMapping("/articles")
    public ResponseEntity<Page<Article>> findAllArticles(@RequestParam(required = false, defaultValue = "0") Integer page) {
        return ResponseEntity.ok().body(articleService.findAllArticles(page));
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<Article> findArticleById(@PathVariable String id) {
        return ResponseEntity.ok().body(articleService.findArticleById(id));
    }

    @GetMapping("/associates")
    public ResponseEntity<Page<Associate>> findAllAssociates(@RequestParam(required = false, defaultValue = "0") Integer page) {
        return ResponseEntity.ok().body(associateService.findAllAssociates(page));
    }

    @GetMapping("/associates/{id}")
    ResponseEntity<Associate> findAssociateById(@PathVariable String id) {
        return ResponseEntity.ok().body(associateService.findAssociateById(id));
    }

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> findAllProducts(@RequestParam(required = false, defaultValue = "0") Integer page) {
        return ResponseEntity.ok().body(productService.findAllProducts(page));
    }


    @GetMapping("/products/search")
    public ResponseEntity<Page<Product>> findAllProductsFiltered(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "") String name) {
        return ResponseEntity.ok().body(productService.findAllProductsByName(name, page));
    }

}
