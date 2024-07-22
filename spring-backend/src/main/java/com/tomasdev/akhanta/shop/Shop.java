package com.tomasdev.akhanta.shop;

import com.tomasdev.akhanta.product.Product;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "shops")
public class Shop {

    @Id
    private String id;
    @Indexed(unique = true)
    private String name;
    @Indexed(unique = true)
    private String seName;
    private String description;
    private String primaryColor;
    private String secondaryColor;
    private String tertiaryColor;
    private String profileImageUrl;
    private String bannerImageUrl;
    @DocumentReference
    private List<Product> products;
    private Double rating;
    private String ownerId;

}
