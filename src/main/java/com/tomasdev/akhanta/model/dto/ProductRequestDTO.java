package com.tomasdev.akhanta.model.dto;

import com.tomasdev.akhanta.model.ProductAttribute;
import com.tomasdev.akhanta.model.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class ProductRequestDTO {

    private String productId;
    private String title;
    private String description;
    private Integer quantity;
    private Float price;
    private List<ProductCategory> categories;
    private List<ProductAttribute> attributes;
    private List<String> images;

}
