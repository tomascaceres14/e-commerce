package com.tomasdev.akhanta.cart;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Document( value = "customers_carts")
public class Cart {

    @Id
    private String cartId;
    private List<CartItem> items;
    private Double totalPrice;
    private String customerId;
    private Date createdAt;
    private Date updatedAt;

    public Cart(String customerId) {
        this.totalPrice = 0.0;
        this.updatedAt = new Date();
        this.items = new ArrayList<>();
        this.customerId = customerId;
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

        totalPrice += item.getItemPrice() * item.getQuantity();
        updatedAt = new Date();
    }

    public void deleteItemFromCart(String productId, boolean unit) {

        for (int i = 0; i < items.size(); i++) {

            CartItem item = items.get(i);

            if (item.getProductId().equals(productId)) {
                if (unit && item.getQuantity() > 1) {
                    totalPrice -= item.getItemPrice();
                    item.setQuantity(item.getQuantity()-1);
                } else {
                    totalPrice -= (item.getItemPrice() * item.getQuantity());
                    items.remove(i);
                }
                break;

            }
        }

        updatedAt = new Date();
    }

    public void clearCart() {
        items.clear();
        totalPrice = 0.0;
        updatedAt = new Date();
    }
}
