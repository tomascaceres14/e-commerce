package com.tomasdev.akhanta.cart;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class CartItemDTO {
    @NotBlank(message = "Por favor, ingrese el id del producto")
    private String productId;
    @NotBlank(message = "Por favor, ingrese una cantidad mayor a 0.")
    private Integer quantity;
}
