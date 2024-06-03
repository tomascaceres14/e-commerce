package com.tomasdev.akhanta.auth;

import com.tomasdev.akhanta.security.jwt.JwtResponseDTO;
import com.tomasdev.akhanta.user.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<JwtResponseDTO> register(@RequestBody UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.register(userDTO));
    }

    @PostMapping(path = "/login")
    public ResponseEntity<JwtResponseDTO> logIn(@RequestBody @Valid UserCredentialsDTO userCredentialsDTO) {
        return ResponseEntity.ok(service.logIn(userCredentialsDTO));
    }

    @PostMapping(path = "/logout")
    public ResponseEntity<String> logOut(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String jwt) {
        service.signOut(jwt);
        return ResponseEntity.status(HttpStatus.RESET_CONTENT).body("Signed out");
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtResponseDTO> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok(service.refreshToken(request, response));
    }
}
