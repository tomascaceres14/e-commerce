package com.tomasdev.akhanta.auth;

import com.tomasdev.akhanta.auth.dto.ShopRegisterDTO;
import com.tomasdev.akhanta.security.jwt.JwtResponseDTO;
import com.tomasdev.akhanta.auth.dto.CustomerRegisterDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/customers/register")
    public ResponseEntity<JwtResponseDTO> customerRegister(@RequestBody CustomerRegisterDTO userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.registerCustomer(userDTO));
    }

    @PostMapping(path = "/shops/register")
    public ResponseEntity<JwtResponseDTO> shopRegister(@RequestBody @Valid ShopRegisterDTO shopDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.registerShop(shopDTO));
    }

    @PostMapping(path = "/customers/login")
    public ResponseEntity<JwtResponseDTO> customerLogIn(@RequestBody @Valid LogInCredentialsDTO credentials) {
        return ResponseEntity.ok(service.logIn(credentials, "CUSTOMER"));
    }

    @PostMapping(path = "/shops/login")
    public ResponseEntity<JwtResponseDTO> shopLogIn(@RequestBody @Valid LogInCredentialsDTO credentials) {
        return ResponseEntity.ok(service.logIn(credentials, "SHOP"));
    }


    @PostMapping(path = "/logout")
    public ResponseEntity<String> logOut(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String jwt) {
        service.signOut(jwt);
        return ResponseEntity.status(HttpStatus.RESET_CONTENT).body("Signed out");
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtResponseDTO> refreshAccessToken(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String refreshToken) {
        return ResponseEntity.ok(service.refreshAccessToken(refreshToken));
    }
}
