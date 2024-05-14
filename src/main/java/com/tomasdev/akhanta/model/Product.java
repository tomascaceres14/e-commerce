package com.tomasdev.akhanta.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Data
@Document(value = "products")
public class Product {

    @Id
    private String productId;
    private String title;
    private String description;
    private Integer stock;
    private Double price;
    @DocumentReference(lazy = true)
    private List<ProductCategory> categories;
    private List<ProductAttribute> attributes;
    private List<String> images;

}
