package com.tomasdev.akhanta.cart;

import jakarta.servlet.http.HttpServletRequest;

public interface CartService {

    String createNewCart(String userId);

    void addItemToCart(CartItemDTO cartItem, HttpServletRequest request);
    void removeItemFromCart(String productId, boolean unit, HttpServletRequest request);

    void clearCart(HttpServletRequest request);

    Cart findCartById(HttpServletRequest request);
}
