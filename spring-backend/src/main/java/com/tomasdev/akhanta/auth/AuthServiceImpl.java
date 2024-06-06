package com.tomasdev.akhanta.auth;

import com.tomasdev.akhanta.exceptions.WrongCredentialsException;
import com.tomasdev.akhanta.user.User;
import com.tomasdev.akhanta.security.jwt.JwtResponseDTO;
import com.tomasdev.akhanta.user.UserDTO;
import com.tomasdev.akhanta.security.jwt.JwtService;
import com.tomasdev.akhanta.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado del logueo de un usuario
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public JwtResponseDTO register(UserDTO userDTO) {

        User user = userService.registerUser(userDTO);

        String accessToken = jwtService.buildAccessToken(user);
        String refreshToken = jwtService.buildRefreshToken(user);

        return new JwtResponseDTO(accessToken, refreshToken);
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

        String accessToken = jwtService.buildAccessToken(user);
        String refreshToken = jwtService.buildRefreshToken(user);

        return new JwtResponseDTO(accessToken, refreshToken);
    }

    /**
     * Cierra la sesión eliminando de la lista blanca el token ingresado
     * @param token Token a eliminar
     */
    public void signOut(String token) {
        String jwt = token.substring(7);
        jwtService.revokeToken(jwt);
    }

    public JwtResponseDTO refreshAccessToken(String refreshToken) {
        String userEmail = JwtService.extractClaim(refreshToken, "email");

        User user = userService.findByEmail(userEmail);
        String accessToken = jwtService.buildAccessToken(user);
        String newRefreshToken = jwtService.buildRefreshToken(user);

        return new JwtResponseDTO(accessToken, newRefreshToken);
    }
}