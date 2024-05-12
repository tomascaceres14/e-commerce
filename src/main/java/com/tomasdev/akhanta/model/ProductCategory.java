package com.tomasdev.akhanta.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class ProductCategory {
    @Id
    private String id;
    private String name;
    private ObjectId parentCategoryId;
}
