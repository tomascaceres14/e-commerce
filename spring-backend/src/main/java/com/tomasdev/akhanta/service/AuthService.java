package com.tomasdev.akhanta.service;

import com.tomasdev.akhanta.model.dto.UserCredentialsDTO;
import com.tomasdev.akhanta.model.dto.JwtResponseDTO;
import com.tomasdev.akhanta.model.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    JwtResponseDTO register(UserDTO userDTO);
    JwtResponseDTO logIn(UserCredentialsDTO userCredentialsDTO);
    JwtResponseDTO refreshToken(HttpServletRequest request, HttpServletResponse response);
    void signOut(String jwt);


}
