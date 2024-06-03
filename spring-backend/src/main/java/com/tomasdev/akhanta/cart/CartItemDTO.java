package com.tomasdev.akhanta.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class CartItemDTO {
    private String productId;
    private Integer quantity;
}
