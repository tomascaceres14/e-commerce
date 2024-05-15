package com.tomasdev.akhanta.controller;

import com.tomasdev.akhanta.model.Cart;
import com.tomasdev.akhanta.model.dto.CartItemDTO;
import com.tomasdev.akhanta.service.CartService;
import com.tomasdev.akhanta.service.UserService;
import com.tomasdev.akhanta.service.dto.ChangePasswordDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
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
    public ResponseEntity<?> addItemToCart(@RequestHeader String productId, HttpServletRequest request) {
        cartService.deleteItemFromCart(productId, request);
        return ResponseEntity.status(HttpStatus.SC_OK).build();
    }

}
