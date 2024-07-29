package com.tomasdev.akhanta.auth;

import com.tomasdev.akhanta.security.jwt.JwtResponseDTO;
import com.tomasdev.akhanta.auth.dto.CustomerRegisterDTO;

public interface AuthService {

    JwtResponseDTO registerUser(CustomerRegisterDTO customerDTO);

    JwtResponseDTO logIn(LogInCredentialsDTO credentials);

    JwtResponseDTO refreshAccessToken(String refreshToken);
    void signOut(String jwt);


}
