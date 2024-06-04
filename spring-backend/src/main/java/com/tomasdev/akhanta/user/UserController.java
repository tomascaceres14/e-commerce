package com.tomasdev.akhanta.user;

import com.tomasdev.akhanta.cart.Cart;
import com.tomasdev.akhanta.cart.CartItemDTO;
import com.tomasdev.akhanta.cart.CartService;
import com.tomasdev.akhanta.auth.ChangePasswordDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final CartService cartService;

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO passwordDTO, HttpServletRequest request) {
        service.changePassword(passwordDTO, request);
        return ResponseEntity.status(HttpStatus.SC_RESET_CONTENT).build();
    }

    @GetMapping("/cart")
    public ResponseEntity<Cart> findCartById(HttpServletRequest request) {
        return ResponseEntity.ok(cartService.findCartById(request));
    }

    @PostMapping("/cart")
    public ResponseEntity<?> addItemToCart(@RequestBody CartItemDTO cartItem, HttpServletRequest request) {
        cartService.addItemToCart(cartItem, request);
        return ResponseEntity.status(HttpStatus.SC_OK).build();
    }

    @DeleteMapping("/cart")
    public ResponseEntity<?> clearCart(HttpServletRequest request) {
        cartService.clearCart(request);
        return ResponseEntity.status(HttpStatus.SC_OK).build();
    }

    @DeleteMapping("/cart/{productId}")
    public ResponseEntity<?> removeItemFromCart(@PathVariable String productId,
                                                @RequestParam(defaultValue = "false") boolean unit,
                                                HttpServletRequest request) {
        cartService.removeItemFromCart(productId, unit, request);
        return ResponseEntity.status(HttpStatus.SC_OK).build();
    }

}
