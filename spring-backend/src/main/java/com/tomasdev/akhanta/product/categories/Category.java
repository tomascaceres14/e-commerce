package com.tomasdev.akhanta.product.categories;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "products_categories")
public class Category {
    @Id
    private String categoryId;
    private String name;
    private String parentId;
    @Indexed(unique = true)
    private String node;
    private List<String> path;
    private String imageUrl;
}
