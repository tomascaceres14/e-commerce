package com.tomasdev.akhanta.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ShopRegisterDTO {

    private String id;
    @NotBlank(message = "Por favor, introduzca el nombre del Shop.")
    private String name;
    private String description;
    private String primaryColor;
    private String secondaryColor;
    private String tertiaryColor;
    private String profileImageUrl;
    private String bannerImageUrl;
    @NotBlank(message = "Missing owner id.")
    private String ownerId;
}