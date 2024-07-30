package com.tomasdev.akhanta.product;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    Page<Product> findAllProducts(int page, int size);
    Product saveProduct(CreateProductDTO productDTO, List<MultipartFile> images, String shopId);
    Product updateProductById(String id, CreateProductDTO product);
    Product findProductById(String id);
    Page<Product> filterProducts(String name, String categoryId,  int page, int size);
    String deleteProductById(String id);
    void removeStockById(String productId, Integer quantity);
}
