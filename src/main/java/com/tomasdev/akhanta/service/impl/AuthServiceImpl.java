package com.tomasdev.akhanta.service.impl;

import com.tomasdev.akhanta.exceptions.ServiceException;
import com.tomasdev.akhanta.exceptions.WrongCredentialsException;
import com.tomasdev.akhanta.model.Token;
import com.tomasdev.akhanta.model.User;
import com.tomasdev.akhanta.model.dto.UserCredentialsDTO;
import com.tomasdev.akhanta.model.dto.JwtResponseDTO;
import com.tomasdev.akhanta.model.dto.UserDTO;
import com.tomasdev.akhanta.security.JwtService;
import com.tomasdev.akhanta.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado del logueo de un usuario
 */
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserServiceImpl userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public JwtResponseDTO register(UserDTO userDTO) {

        User user = userService.registerUser(userDTO);

        Token accessToken = jwtService.generateAccessToken(user);
        Token refreshToken = jwtService.generateRefreshToken(user);

        return new JwtResponseDTO(accessToken.getToken(), refreshToken.getToken());
    }


    /**
     * Devuelve un dto con el jwt de acceso y de refresco del usuario dadas unas credenciales
     * @param credentials Credenciales de acceso
     * @return Dto con el jwt del usuario si las credenciales son validas
     */
    @Override
    public JwtResponseDTO logIn(UserCredentialsDTO credentials) {

        User user = userService.findByEmail(credentials.getEmail());

        if (!passwordEncoder.matches(credentials.getPassword(), user.getPassword())) {
            throw new WrongCredentialsException();
        }

        Token accessToken = jwtService.generateAccessToken(user);
        Token refreshToken = jwtService.generateRefreshToken(user);

        return new JwtResponseDTO(accessToken.getToken(), refreshToken.getToken());
    }

    /**
     * Cierra la sesión eliminando de la lista blanca el token ingresado
     * @param token Token a eliminar
     */
    public void signOut(String token) {
        String jwt = token.substring(7);
        jwtService.deleteToken(jwt);
    }

    @Override
    public JwtResponseDTO refreshToken(HttpServletRequest request, HttpServletResponse response) {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith("Bearer ")) {
            return null;
        }

        String refreshToken = header.substring(7);
        String userEmail = jwtService.extractUserEmail(refreshToken);

        if (userEmail == null) throw new ServiceException("Bad token. No email present");

        User user = userService.findByEmail(userEmail);

        if (!jwtService.isTokenValid(refreshToken, user)) throw new ServiceException("Invalid token.");

        Token accessToken = jwtService.generateAccessToken(user);
        //TODO revoke/delete tokens
        //TODO save token

        return new JwtResponseDTO(accessToken.getToken(), refreshToken);

    }

}