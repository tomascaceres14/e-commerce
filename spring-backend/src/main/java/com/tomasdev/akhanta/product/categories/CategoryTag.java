package com.tomasdev.akhanta.product.categories;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Data
@Document(collection = "products_categories")
public class CategoryTag {
    @Id
    private String id;
    private String name;
    private String parentCategory;
    @DocumentReference
    private List<CategoryTag> subCategories;

    public void addSubCategory(CategoryTag tag) {
        subCategories.add(tag);
    }
}
