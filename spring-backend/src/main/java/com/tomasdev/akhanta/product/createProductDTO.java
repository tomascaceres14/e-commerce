package com.tomasdev.akhanta.product;

import com.tomasdev.akhanta.product.categories.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class createProductDTO {

    private String productId;
    private String title;
    private String description;
    private Integer stock;
    private Double price;
    private String categoryId;
    private List<ProductAttribute> attributes;

}
