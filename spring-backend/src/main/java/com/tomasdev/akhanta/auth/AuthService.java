package com.tomasdev.akhanta.auth;

import com.tomasdev.akhanta.security.jwt.JwtResponseDTO;
import com.tomasdev.akhanta.users.UserDTO;
import com.tomasdev.akhanta.users.customer.CustomerDTO;

public interface AuthService {

    JwtResponseDTO customerRegister(CustomerDTO customerDTO);
    JwtResponseDTO customerLogIn(UserCredentialsDTO userCredentialsDTO);
    JwtResponseDTO refreshAccessToken(String refreshToken);
    void signOut(String jwt);


}
