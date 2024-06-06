package com.tomasdev.akhanta.user;

import com.tomasdev.akhanta.cart.Cart;
import com.tomasdev.akhanta.cart.CartItemDTO;
import com.tomasdev.akhanta.cart.CartService;
import com.tomasdev.akhanta.auth.ChangePasswordDTO;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final CartService cartService;

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO passwordDTO, @RequestHeader(name = HttpHeaders.AUTHORIZATION) String jwt) {
        service.changePassword(passwordDTO, jwt);
        return ResponseEntity.status(HttpStatus.SC_RESET_CONTENT).build();
    }

    @GetMapping("/cart")
    public ResponseEntity<Cart> findCartById(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String jwt) {
        return ResponseEntity.ok(cartService.findCartById(jwt));
    }

    @PostMapping("/cart")
    public ResponseEntity<?> addItemToCart(@RequestBody CartItemDTO cartItem, @RequestHeader(name = HttpHeaders.AUTHORIZATION) String jwt) {
        cartService.addItemToCart(cartItem, jwt);
        return ResponseEntity.status(HttpStatus.SC_OK).build();
    }

    @DeleteMapping("/cart")
    public ResponseEntity<?> clearCart(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String jwt) {
        cartService.clearCart(jwt);
        return ResponseEntity.status(HttpStatus.SC_OK).build();
    }

    @DeleteMapping("/cart/{productId}")
    public ResponseEntity<?> removeItemFromCart(@PathVariable String productId,
                                                @RequestParam(defaultValue = "false") boolean unit,
                                                @RequestHeader(name = HttpHeaders.AUTHORIZATION) String jwt) {
        cartService.removeItemFromCart(productId, unit, jwt);
        return ResponseEntity.status(HttpStatus.SC_OK).build();
    }

}
