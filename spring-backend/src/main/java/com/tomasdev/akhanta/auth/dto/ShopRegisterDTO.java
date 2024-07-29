package com.tomasdev.akhanta.auth.dto;

import lombok.Data;

@Data
public class ShopRegisterDTO {
    private String id;
    private String name;
    private String description;
    private String primaryColor;
    private String secondaryColor;
    private String tertiaryColor;
    private String profileImageUrl;
    private String bannerImageUrl;
    private String ownerId;
}