package com.tomasdev.akhanta.home;


import com.tomasdev.akhanta.product.Product;
import com.tomasdev.akhanta.product.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/home/products")
@AllArgsConstructor
public class HomeProductController {

    private ProductService productService;

    @GetMapping
    public ResponseEntity<Page<Product>> findAllProducts(@RequestParam(required = false, defaultValue = "0") Integer page) {
        return ResponseEntity.ok().body(productService.findAllProducts(page));
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> findProductById(@PathVariable String id) {
        return ResponseEntity.ok().body(productService.findProductById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Product>> findAllProductsFiltered(
            @RequestParam(required = false, defaultValue = "") String categoryId,
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        return ResponseEntity.ok().body(productService.filterProducts(name, categoryId, page, size));
    }

    @GetMapping("/categories")
    public ResponseEntity<?> findAllCategories(@RequestParam(required = false, defaultValue = "0") Integer page) {
        return ResponseEntity.ok().build();
    }
}
