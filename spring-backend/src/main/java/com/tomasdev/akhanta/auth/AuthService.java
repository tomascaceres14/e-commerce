package com.tomasdev.akhanta.auth;

import com.tomasdev.akhanta.security.jwt.JwtResponseDTO;
import com.tomasdev.akhanta.auth.dto.CustomerRegisterDTO;
import com.tomasdev.akhanta.auth.dto.ShopRegisterDTO;

public interface AuthService {

    JwtResponseDTO registerCustomer(CustomerRegisterDTO customerDTO);
    JwtResponseDTO registerShop(ShopRegisterDTO shopDTO);

    JwtResponseDTO logIn(UserCredentialsDTO credentials, String role);

    JwtResponseDTO customerLogIn(UserCredentialsDTO userCredentialsDTO);
    JwtResponseDTO refreshAccessToken(String refreshToken);
    void signOut(String jwt);


}
