package com.tomasdev.akhanta.controller;

import com.tomasdev.akhanta.model.dto.UserCredentialsDTO;
import com.tomasdev.akhanta.model.dto.JwtResponseDTO;
import com.tomasdev.akhanta.model.dto.UserDTO;
import com.tomasdev.akhanta.model.dto.ResponseUserDTO;
import com.tomasdev.akhanta.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;
    private final ModelMapper mapper;

    @PostMapping("/register")
    public ResponseEntity<ResponseUserDTO> register(@RequestBody UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.map(service.register(userDTO), ResponseUserDTO.class));
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
