package com.tomasdev.akhanta.cart;

import jakarta.servlet.http.HttpServletRequest;

public interface CartService {

    String createNewCart(String userId);

    void addItemToCart(CartItemDTO cartItem, String jwt);
    void removeItemFromCart(String productId, boolean unit, String jwt);

    void clearCart(String jwt);

    Cart findCartById(String jwt);
}
