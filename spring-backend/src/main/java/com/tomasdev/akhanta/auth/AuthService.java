package com.tomasdev.akhanta.auth;

import com.tomasdev.akhanta.security.jwt.JwtResponseDTO;
import com.tomasdev.akhanta.auth.dto.CustomerRegisterDTO;
import com.tomasdev.akhanta.auth.dto.ShopRegisterDTO;

public interface AuthService {

    JwtResponseDTO customerRegister(CustomerRegisterDTO customerDTO);
    JwtResponseDTO shopRegister(ShopRegisterDTO shopDTO);
    JwtResponseDTO customerLogIn(UserCredentialsDTO userCredentialsDTO);
    JwtResponseDTO refreshAccessToken(String refreshToken);
    void signOut(String jwt);


}
