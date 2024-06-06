package com.tomasdev.akhanta.cart;

import com.tomasdev.akhanta.exceptions.ResourceNotFoundException;
import com.tomasdev.akhanta.product.Product;
import com.tomasdev.akhanta.product.ProductService;
import com.tomasdev.akhanta.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final ModelMapper mapper;
    private final CartRepository repository;
    private final ProductService productService;

    @Override
    public String createNewCart(String userId) {
        Cart cart = new Cart(userId);
        return repository.save(cart).getCartId();
    }

    public Cart findCartById(String jwt) {
        String id = JwtService.extractClaimWithBearer(jwt, "cartId");
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(STR."Carrito id \{id} no encontrado"));
    }

    @Override
    public void addItemToCart(CartItemDTO cartItemDTO, String jwt) {
        Cart cart = findCartById(jwt);

        CartItem item = mapper.map(cartItemDTO, CartItem.class);
        Product product = productService.findProductById(cartItemDTO.getProductId());

        item.setPrice(product.getPrice());
        item.setName(product.getTitle());

        cart.addItemToCart(item);
        repository.save(cart);
    }

    @Override
    public void removeItemFromCart(String productId, boolean unit, String jwt) {
        Cart cart = findCartById(jwt);
        cart.deleteItemFromCart(productId, unit);
        repository.save(cart);
    }

    @Override
    public void clearCart(String jwt) {
        Cart cart = findCartById(jwt);
        cart.clearCart();
        repository.save(cart);
    }
}
