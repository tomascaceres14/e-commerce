package com.tomasdev.akhanta.auth;

import com.tomasdev.akhanta.security.jwt.JwtResponseDTO;
import com.tomasdev.akhanta.user.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    JwtResponseDTO register(UserDTO userDTO);
    JwtResponseDTO logIn(UserCredentialsDTO userCredentialsDTO);
    JwtResponseDTO refreshAccessToken(String refreshToken);
    void signOut(String jwt);


}
