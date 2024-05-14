package com.tomasdev.akhanta.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Document( value = "users_carts")
public class Cart {

    @Id
    private String cartId;
    private Date orderedAt;
    private List<CartItem> items;
    private Double payAmount;
    private Double discountAmount;
    private String userId;

    public Cart(String userId) {
        this.payAmount = 0.0;
        this.discountAmount = 0.0;
        this.orderedAt = new Date();
        this.items = new ArrayList<>();
        this.userId = userId;
    }

    public void addItemToCart(CartItem item) {
        items.add(item);
        payAmount += item.getPrice();
    }

}
