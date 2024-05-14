package com.tomasdev.akhanta.service;

import com.tomasdev.akhanta.model.Cart;
import com.tomasdev.akhanta.model.dto.CartItemDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface CartService {

    String createNewCart(String userId);

    void addItemToCart(CartItemDTO cartItem, HttpServletRequest request);

    Cart findCartById(HttpServletRequest request);
}
