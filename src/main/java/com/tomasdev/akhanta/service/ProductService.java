package com.tomasdev.akhanta.service;

import com.tomasdev.akhanta.model.Product;

public interface ProductService {

    Product saveProduct();
    Product updateProductById();
    Product findProductById();
    Product findAllProductsById();
    Product findProductByName();
    Product findAllProductsByName();
    Product findProductByCategory();
    Product findAllProductsByCategory();
}
