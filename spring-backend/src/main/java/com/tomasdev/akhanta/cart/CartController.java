package com.tomasdev.akhanta.cart;


import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService service;

    @GetMapping("/cart")
    public ResponseEntity<Cart> findCartById(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String jwt) {
        return ResponseEntity.ok(service.findCartById(jwt));
    }

    @PostMapping("/cart")
    public ResponseEntity<?> addItemToCart(@RequestBody CartItemDTO cartItem,
                                           @RequestHeader(name = HttpHeaders.AUTHORIZATION) String jwt) {
        service.addItemToCart(cartItem, jwt);
        return ResponseEntity.status(HttpStatus.SC_OK).build();
    }

    @DeleteMapping("/cart")
    public ResponseEntity<?> clearCart(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String jwt) {
        service.clearCart(jwt);
        return ResponseEntity.status(HttpStatus.SC_OK).build();
    }

    @DeleteMapping("/cart/{productId}")
    public ResponseEntity<?> removeItemFromCart(@PathVariable String productId,
                                                @RequestParam(defaultValue = "false") boolean unit,
                                                @RequestHeader(name = HttpHeaders.AUTHORIZATION) String jwt) {
        service.removeItemFromCart(productId, unit, jwt);
        return ResponseEntity.status(HttpStatus.SC_OK).build();
    }
}
