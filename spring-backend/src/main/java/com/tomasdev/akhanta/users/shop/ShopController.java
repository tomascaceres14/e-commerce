package com.tomasdev.akhanta.users.shop;

import com.tomasdev.akhanta.auth.PasswordChangeDTO;
import com.tomasdev.akhanta.product.CreateProductDTO;
import com.tomasdev.akhanta.product.Product;
import com.tomasdev.akhanta.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/shops")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService service;
    private final ProductService productService;

    @PostMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeDTO passwordDTO,
                                            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String jwt) {
        service.changePassword(passwordDTO, jwt);
        return ResponseEntity.status(HttpStatus.SC_RESET_CONTENT).build();
    }

    @PostMapping("/products")
    public ResponseEntity<Product> saveProducts(@Valid @RequestPart CreateProductDTO product,
                                                @RequestPart(required = false) List<MultipartFile> images,
                                                @RequestHeader(name = HttpHeaders.AUTHORIZATION) String jwt) {
        log.info("[ /admin/products - POST ]");

        return ResponseEntity
                .status(org.springframework.http.HttpStatus.CREATED)
                .body(productService.saveProduct(product, images, jwt));
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProductById(@PathVariable String id,
                                                     @RequestPart(required = false) CreateProductDTO product) {
        log.info("[ /admin/products/id - PUT ]");
        return ResponseEntity.status(org.springframework.http.HttpStatus.CREATED)
                .body(productService.updateProductById(id, product));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable String id) {
        log.info("[ /admin/products/id - DELETE ]");
        productService.deleteProductById(id);
        return ResponseEntity.ok(STR."Producto id \{id} eliminado.");
    }

}
