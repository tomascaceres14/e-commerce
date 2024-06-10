package com.tomasdev.akhanta.admin;

import com.tomasdev.akhanta.article.Article;
import com.tomasdev.akhanta.associate.Associate;
import com.tomasdev.akhanta.auth.AuthService;
import com.tomasdev.akhanta.product.categories.Category;
import com.tomasdev.akhanta.product.Product;
import com.tomasdev.akhanta.article.ArticleRequestDTO;
import com.tomasdev.akhanta.associate.AssociateRequestDTO;
import com.tomasdev.akhanta.product.ProductRequestDTO;
import com.tomasdev.akhanta.article.ArticleService;
import com.tomasdev.akhanta.associate.AssociateService;
import com.tomasdev.akhanta.product.categories.CategoryService;
import com.tomasdev.akhanta.product.ProductService;
import com.tomasdev.akhanta.security.jwt.JwtResponseDTO;
import com.tomasdev.akhanta.auth.dto.ShopRegisterDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private AssociateService associateService;
    private ProductService productService;
    private CategoryService categoryService;
    private AuthService authService;

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

    @DeleteMapping("/articles/{id}")
    public ResponseEntity<String> deleteArticleById(@PathVariable String id) {
        log.info("[ /admin/articles/id - DELETE ]");
        articleService.deleteArticleById(id);
        return ResponseEntity.ok(STR."Articulo id \{id} eliminado.");
    }

    @PostMapping("/associates")
    public ResponseEntity<Associate> saveAssociate(@Valid @RequestPart AssociateRequestDTO associate,
                                            @RequestPart MultipartFile profile,
                                            @RequestPart MultipartFile banner) {
        log.info("[ /admin/associates - POST ]");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(associateService.saveAssociate(associate, profile, banner));
    }

    @PutMapping("/associates/{id}")
    public ResponseEntity<Associate> updateAssociateById(@PathVariable String id,
                                                  @RequestPart(required = false) AssociateRequestDTO associate,
                                                  @RequestPart(required = false) MultipartFile profile,
                                                  @RequestPart(required = false) MultipartFile banner) {
        log.info("[ /admin/associates/id - PUT ]");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(associateService.updateAssociateById(id, associate, profile, banner));
    }

    @DeleteMapping("/associates/{id}")
    public ResponseEntity<String> deleteAssociatesById(@PathVariable String id) {
        log.info("[ /admin/associates/id - DELETE ]");
        associateService.deleteAssociateById(id);
        return ResponseEntity.ok(STR."Asociado id \{id} eliminado.");
    }

    @PostMapping("/products")
    public ResponseEntity<Product> saveProducts(@Valid @RequestPart ProductRequestDTO product,
                                                @RequestPart(required = false) List<MultipartFile> images) {
        log.info("[ /admin/products - POST ]");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.saveProduct(product, images));
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProductById(@PathVariable String id,
                                                         @RequestPart(required = false) ProductRequestDTO product) {
        log.info("[ /admin/products/id - PUT ]");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.updateProductById(id, product));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable String id) {
        log.info("[ /admin/products/id - DELETE ]");
        productService.deleteProductById(id);
        return ResponseEntity.ok(STR."Producto id \{id} eliminado.");
    }

    @PostMapping("/products/categories")
    public ResponseEntity<Category> saveCategory(@RequestBody Category tag) {
        log.info("[ /admin/products/tags - POST ]");
        categoryService.saveCategory(tag);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/products/categories")
    public ResponseEntity<List<Category>> findAllCategories() {
        log.info("[ /admin/products/tags - GET ]");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryService.findAllCategories());
    }

    @GetMapping("/products/categories/{id}")
    public ResponseEntity<Category> findCategoryById(@RequestParam String id) {
        log.info("[ /admin/products/tags - GET ]");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(categoryService.findCategoryById(id));
    }

    @PostMapping(path = "/shop/register")
    public ResponseEntity<JwtResponseDTO> shopRegister(@RequestBody @Valid ShopRegisterDTO shopDTO) {
        return ResponseEntity.ok(authService.shopRegister(shopDTO));
    }
}