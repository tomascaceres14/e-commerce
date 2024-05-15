package com.tomasdev.akhanta.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class CartItemDTO {
    private String productId;
    private String name;
}
