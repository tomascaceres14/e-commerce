package com.tomasdev.akhanta.service.impl;

import com.tomasdev.akhanta.exceptions.PasswordIncorrectException;
import com.tomasdev.akhanta.model.User;
import com.tomasdev.akhanta.model.dto.AuthUserDTO;
import com.tomasdev.akhanta.model.dto.JwtResponseDTO;
import com.tomasdev.akhanta.model.dto.UserDTO;
import com.tomasdev.akhanta.repository.UserRepository;
import com.tomasdev.akhanta.security.JwtAuthenticationProvider;
import com.tomasdev.akhanta.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserServiceImpl userService;

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper mapper;

    /**
     * Devuelve un dto con el jwt del usuario dadas unas credenciales
     * @param authUserDto Credenciales de acceso
     * @return Dto con el jwt del usuario si las credenciales son validas
     */
    public JwtResponseDTO signIn(AuthUserDTO authUserDto) {

        User user = userService.findByEmail(authUserDto.getEmail());

        if (!passwordEncoder.matches(authUserDto.getPassword(), user.getPassword())) {
            throw new PasswordIncorrectException();
        }

        return new JwtResponseDTO(jwtAuthenticationProvider.createToken(mapper.map(user, UserDTO.class)));
    }

    /**
     * Cierra la sesión eliminando de la lista blanca el token ingresado
     * @param token Token a eliminar
     * @return
     */
    public JwtResponseDTO signOut(String token) {

        String[] authElements = token.split(" ");
        return new JwtResponseDTO(jwtAuthenticationProvider.deleteToken(authElements[1]));
    }
}
