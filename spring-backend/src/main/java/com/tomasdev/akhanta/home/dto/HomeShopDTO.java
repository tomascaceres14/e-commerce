package com.tomasdev.akhanta.home.dto;

import com.tomasdev.akhanta.product.Product;
import lombok.Data;

import java.util.List;

@Data
public class HomeShopDTO {

    private String shopId;
    private String name;
    private String description;
    private String primaryColor;
    private String secondaryColor;
    private String tertiaryColor;
    private String profileImageUrl;
    private String bannerImageUrl;
    private List<Product> products;

}
