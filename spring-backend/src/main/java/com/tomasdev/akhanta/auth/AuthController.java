package com.tomasdev.akhanta.auth;

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

    @PostMapping("/customer/register")
    public ResponseEntity<JwtResponseDTO> customerRegister(@RequestBody CustomerRegisterDTO userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.registerCustomer(userDTO));
    }

    @PostMapping(path = "/{role}/login")
    public ResponseEntity<JwtResponseDTO> logIn(@RequestBody @Valid UserCredentialsDTO userCredentialsDTO) {
        return ResponseEntity.ok(service.customerLogIn(userCredentialsDTO));
    }

    @PostMapping(path = "/customer/login")
    public ResponseEntity<JwtResponseDTO> customerLogIn(@RequestBody @Valid UserCredentialsDTO userCredentialsDTO) {
        return ResponseEntity.ok(service.customerLogIn(userCredentialsDTO));
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
