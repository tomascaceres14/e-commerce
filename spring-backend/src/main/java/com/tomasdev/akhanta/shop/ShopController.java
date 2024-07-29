package com.tomasdev.akhanta.shop;

import com.tomasdev.akhanta.auth.dto.ShopRegisterDTO;
import com.tomasdev.akhanta.orders.ShopOrder;
import com.tomasdev.akhanta.orders.ShopOrderService;
import com.tomasdev.akhanta.product.CreateProductDTO;
import com.tomasdev.akhanta.product.Product;
import com.tomasdev.akhanta.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
    private final ShopOrderService orderService;

    @PostMapping
    public ResponseEntity<Shop> saveShop(@RequestBody ShopRegisterDTO shopDTO) {
        return ResponseEntity.ok(service.saveShop(shopDTO));
    }

    @PostMapping("/products")
    public ResponseEntity<Product> saveProduct(@Valid @RequestPart CreateProductDTO product,
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
        return ResponseEntity.ok(productService.deleteProductById(id));
    }

    @GetMapping("/orders")
    public ResponseEntity<Page<ShopOrder>> findAllOrdersByShop(@RequestParam(required = false, defaultValue = "") String customerId,
                                                         @RequestParam(required = false, defaultValue = "0") Integer page,
                                                         @RequestHeader(name = HttpHeaders.AUTHORIZATION) String jwt) {
        return ResponseEntity.ok(orderService.findAllOrdersByShop(jwt, customerId, page));
    }
}
