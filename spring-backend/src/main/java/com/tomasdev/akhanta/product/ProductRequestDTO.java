package com.tomasdev.akhanta.product;

import com.tomasdev.akhanta.product.categories.CategoryTag;
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
    private List<CategoryTag> categories;
    private List<ProductAttribute> attributes;
    private List<String> images;

}
