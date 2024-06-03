package com.tomasdev.akhanta.product;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private String categoryId;
    private List<ProductAttribute> attributes;
    private List<String> images;

}
