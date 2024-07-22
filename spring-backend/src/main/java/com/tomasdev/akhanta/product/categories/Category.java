package com.tomasdev.akhanta.product.categories;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "categories")
public class Category {
    @Id
    private String categoryId;
    private String name;
    @Indexed(unique = true)
    private String node;
    private String imageUrl;
}
