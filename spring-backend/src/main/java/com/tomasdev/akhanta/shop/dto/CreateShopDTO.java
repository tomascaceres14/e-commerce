package com.tomasdev.akhanta.shop.dto;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
public class CreateShopDTO {
    @Indexed(unique = true)
    private String name;
    private String description;
    private String primaryColor;
    private String secondaryColor;
    private String tertiaryColor;
    private String profileImageUrl;
    private String bannerImageUrl;
    private String ownerId;

}
