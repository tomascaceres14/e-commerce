package com.tomasdev.akhanta.product;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    Page<Product> findAllProducts(int page);
    Product saveProduct(createProductDTO productDTO, List<MultipartFile> images);
    Product updateProductById(String id, createProductDTO product);
    Product findProductById(String id);
    Page<Product> filterProducts(String name, String categoryId, int page);
    String deleteProductById(String id);
}
