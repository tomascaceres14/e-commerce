package com.tomasdev.akhanta.service;

import com.tomasdev.akhanta.model.dto.AuthUserDTO;
import com.tomasdev.akhanta.model.dto.JwtResponseDTO;

public interface AuthService {

    JwtResponseDTO signIn(AuthUserDTO authUserDTO);

    JwtResponseDTO signOut(String jwt);

}
