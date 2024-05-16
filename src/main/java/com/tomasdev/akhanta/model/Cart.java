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
    private Date lastUpdate;
    private List<CartItem> items;
    private Double payAmount;
    private Double discountAmount;
    private String userId;

    public Cart(String userId) {
        this.payAmount = 0.0;
        this.discountAmount = 0.0;
        this.lastUpdate = new Date();
        this.items = new ArrayList<>();
        this.userId = userId;
    }

    public void addItemToCart(CartItem item) {
        boolean isPresent = false;

        for (CartItem i: items) {
            if (i.getProductId().equals(item.getProductId())) {
                i.setQuantity(i.getQuantity() + item.getQuantity());
                isPresent = true;
                break;
            }
        }

        if (!isPresent) {
            items.add(item);
        }

        payAmount += item.getPrice() * item.getQuantity();
        lastUpdate = new Date();
    }

    public void deleteItemFromCart(String productId, boolean unit) {

        for (int i = 0; i < items.size(); i++) {

            CartItem item = items.get(i);

            if (item.getProductId().equals(productId)) {
                if (unit && item.getQuantity() > 1) {
                    payAmount -= item.getPrice();
                    item.setQuantity(item.getQuantity()-1);
                } else {
                    payAmount -= (item.getPrice() * item.getQuantity());
                    items.remove(i);
                }
                break;

            }
        }

        lastUpdate = new Date();
    }

    public void clearCart() {
        items.clear();
        payAmount = 0.0;
        discountAmount = 0.0;
        lastUpdate = new Date();
    }
}
