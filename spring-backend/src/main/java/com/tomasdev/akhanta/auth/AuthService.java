package com.tomasdev.akhanta.auth;

import com.tomasdev.akhanta.security.jwt.JwtResponseDTO;
import com.tomasdev.akhanta.user.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    JwtResponseDTO register(UserDTO userDTO);
    JwtResponseDTO logIn(UserCredentialsDTO userCredentialsDTO);
    JwtResponseDTO refreshToken(HttpServletRequest request, HttpServletResponse response);
    void signOut(String jwt);


}
