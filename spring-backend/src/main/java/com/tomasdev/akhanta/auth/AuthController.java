package com.tomasdev.akhanta.auth;

import com.tomasdev.akhanta.auth.dto.LogInCredentialsDTO;
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

    @PostMapping("/register")
    public ResponseEntity<JwtResponseDTO> registerUser(@RequestBody CustomerRegisterDTO userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.registerUser(userDTO));
    }

    @PostMapping(path = "/login")
    public ResponseEntity<JwtResponseDTO> logIn(@RequestBody @Valid LogInCredentialsDTO credentials) {
        return ResponseEntity.ok(service.logIn(credentials));
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
