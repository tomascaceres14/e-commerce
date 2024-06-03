package com.tomasdev.akhanta.cart;

import com.tomasdev.akhanta.exceptions.ResourceNotFoundException;
import com.tomasdev.akhanta.product.Product;
import com.tomasdev.akhanta.product.ProductServiceImpl;
import com.tomasdev.akhanta.security.jwt.JwtService;
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
        String id = extractCartId(request);

        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(STR."Carrito id \{id} no encontrado"));
    }

    @Override
    public void addItemToCart(CartItemDTO cartItemDTO, HttpServletRequest request) {
        String cartId = extractCartId(request);

        Cart cart = repository.findById(cartId).orElseThrow(() ->
                new ResourceNotFoundException(STR."Carrito id \{cartId} no encontrado"));

        CartItem item = mapper.map(cartItemDTO, CartItem.class);
        Product product = productService.findProductById(cartItemDTO.getProductId());

        item.setPrice(product.getPrice());
        item.setName(product.getTitle());

        cart.addItemToCart(item);

        repository.save(cart);
    }

    @Override
    public void removeItemFromCart(String productId, boolean unit, HttpServletRequest request) {
        String cartId = extractCartId(request);

        Cart cart = repository.findById(cartId).orElseThrow(() ->
                new ResourceNotFoundException(STR."Carrito id \{cartId} no encontrado"));

       cart.deleteItemFromCart(productId, unit);
       repository.save(cart);
    }

    @Override
    public void clearCart(HttpServletRequest request) {
        String cartId = extractCartId(request);

        Cart cart = repository.findById(cartId).orElseThrow(() ->
                new ResourceNotFoundException(STR."Carrito id \{cartId} no encontrado"));

        cart.clearCart();
        repository.save(cart);
    }

    private String extractCartId(HttpServletRequest request) {
        return JwtService.extractClaim(
                request.getHeader(HttpHeaders.AUTHORIZATION)
                        .substring(7), "cart");
    }
}
