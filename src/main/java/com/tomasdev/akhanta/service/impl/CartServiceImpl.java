package com.tomasdev.akhanta.service.impl;

import com.tomasdev.akhanta.exceptions.ResourceNotFoundException;
import com.tomasdev.akhanta.model.Cart;
import com.tomasdev.akhanta.model.CartItem;
import com.tomasdev.akhanta.model.Product;
import com.tomasdev.akhanta.model.dto.CartItemDTO;
import com.tomasdev.akhanta.repository.CartRepository;
import com.tomasdev.akhanta.security.JwtService;
import com.tomasdev.akhanta.service.CartService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final ModelMapper mapper;
    private final CartRepository repository;
    private final ProductServiceImpl productService;

    @Override
    public String createNewCart(String userId) {
        Cart cart = new Cart(userId);
        cart.setUserId(userId);
        return repository.save(cart).getCartId();
    }

    public Cart findCartById(HttpServletRequest request) {
        String id = JwtService.extractClaim(
                request.getHeader(HttpHeaders.AUTHORIZATION)
                        .substring(7), "cart");

        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(STR."Carrito id \{id} no encontrado"));
    }

    @Override
    public void addItemToCart(CartItemDTO cartItemDTO, HttpServletRequest request) {
        String cartId = JwtService.extractClaim(
                request.getHeader(HttpHeaders.AUTHORIZATION)
                        .substring(7), "cart");

        Cart cart = repository.findById(cartId).orElseThrow(() ->
                new ResourceNotFoundException(STR."Carrito id \{cartId} no encontrado"));

        CartItem item = mapper.map(cartItemDTO, CartItem.class);
        Product product = productService.findProductById(cartItemDTO.getProductId());

        item.setPrice(product.getPrice());
        item.setQuantity(1);

        cart.addItemToCart(item);

        repository.save(cart);
    }

    @Override
    public void removeItemFromCart(String productId, boolean unit, HttpServletRequest request) {
        String cartId = JwtService.extractClaim(
                request.getHeader(HttpHeaders.AUTHORIZATION)
                        .substring(7), "cart");

        Cart cart = repository.findById(cartId).orElseThrow(() ->
                new ResourceNotFoundException(STR."Carrito id \{cartId} no encontrado"));

       cart.deleteItemFromCart(productId, unit);
       repository.save(cart);
    }

    @Override
    public void clearCart(HttpServletRequest request) {
        String cartId = JwtService.extractClaim(
                request.getHeader(HttpHeaders.AUTHORIZATION)
                        .substring(7), "cart");

        Cart cart = repository.findById(cartId).orElseThrow(() ->
                new ResourceNotFoundException(STR."Carrito id \{cartId} no encontrado"));

        cart.clearCart();
        repository.save(cart);
    }
}
