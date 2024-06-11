package com.tomasdev.akhanta.product;

import lombok.Data;

import java.util.List;

@Data
public class CreateProductDTO {
    private String productId;
    private String title;
    private String description;
    private Integer stock;
    private Double price;
    private String categoryId;
    private List<ProductAttribute> attributes;
}
