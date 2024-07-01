package com.tomasdev.akhanta.admin;

import com.tomasdev.akhanta.article.Article;
import com.tomasdev.akhanta.product.Product;
import com.tomasdev.akhanta.product.ProductService;
import com.tomasdev.akhanta.product.categories.Category;
import com.tomasdev.akhanta.article.ArticleRequestDTO;
import com.tomasdev.akhanta.article.ArticleService;
import com.tomasdev.akhanta.product.categories.CategoryService;
import com.tomasdev.akhanta.users.customer.Customer;
import com.tomasdev.akhanta.users.customer.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

    private ArticleService articleService;
    private CategoryService categoryService;
    private final CustomerService customerService;
    private final ProductService productService;

    @PostMapping("/articles")
    public ResponseEntity<Article> saveArticle(@Valid @RequestPart ArticleRequestDTO article,
                                        @RequestPart MultipartFile image) {
        log.info("[ /admin/articles - POST ]");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(articleService.saveArticle(article, image));
    }

    @PutMapping("/articles/{id}")
    public ResponseEntity<Article> updateArticleById(@PathVariable String id,
                                              @RequestPart(required = false) ArticleRequestDTO article,
                                              @RequestPart(required = false) MultipartFile image) {
        log.info("[ /admin/articles/id - PUT ]");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(articleService.updateArticleById(id, article, image));
    }

    @GetMapping("/customers")
    public ResponseEntity<Page<Customer>> findAllCustomers(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                           @RequestParam(required = false, defaultValue = "10") Integer size) {
        return ResponseEntity.ok().body(customerService.findAll(page, size));
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<String> deleteCustomerById(@PathVariable String id) {
        log.info("[ /admin/articles/id - DELETE ]");
        customerService.deleteById(id);
        return ResponseEntity.ok(STR."Cliente id \{id} eliminado.");
    }

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> findAllProducts(@RequestParam(required = false, defaultValue = "0") Integer page,
                                                         @RequestParam(required = false, defaultValue = "10") Integer size) {
        log.info("[ /admin/products/categories - GET ]");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.findAllProducts(page, size));
    }

    @GetMapping("/products/categories/{id}")
    public ResponseEntity<Category> findProductById(@RequestParam String id) {
        log.info("[ /admin/products/categories - GET ]");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryService.findCategoryById(id));
    }

    @PostMapping("/products/categories")
    public ResponseEntity<Category> saveCategory(@RequestBody Category tag) {
        log.info("[ /admin/products/categories - POST ]");
        categoryService.saveCategory(tag);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/products/categories")
    public ResponseEntity<List<Category>> findAllCategories() {
        log.info("[ /admin/products/categories - GET ]");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryService.findAllCategories());
    }

    @GetMapping("/products/categories/{id}")
    public ResponseEntity<Category> findCategoryById(@RequestParam String id) {
        log.info("[ /admin/products/categories - GET ]");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryService.findCategoryById(id));
    }


}