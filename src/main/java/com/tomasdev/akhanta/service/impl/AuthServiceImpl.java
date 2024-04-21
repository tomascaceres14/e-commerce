package com.tomasdev.akhanta.service.impl;

import com.tomasdev.akhanta.exceptions.WrongCredentialsException;
import com.tomasdev.akhanta.model.User;
import com.tomasdev.akhanta.model.dto.AuthUserDTO;
import com.tomasdev.akhanta.model.dto.JwtResponseDTO;
import com.tomasdev.akhanta.model.dto.UserDTO;
import com.tomasdev.akhanta.security.JwtAuthenticationProvider;
import com.tomasdev.akhanta.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado del logueo de un usuario
 */
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserServiceImpl userRepository;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final PasswordEncoder passwordEncoder;

    private final ModelMapper mapper;

    /**
     * Devuelve un dto con el jwt del usuario dadas unas credenciales
     * @param authCustomerDto Credenciales de acceso
     * @return Dto con el jwt del usuario si las credenciales son validas
     */
    @Override
    public JwtResponseDTO signIn(AuthUserDTO authCustomerDto) {

        User user = userRepository.findByEmail(authCustomerDto.getEmail());

        if (!passwordEncoder.matches(authCustomerDto.getPassword(), user.getPassword())) {
            throw new WrongCredentialsException();
        }

        return new JwtResponseDTO(jwtAuthenticationProvider.createToken(mapper.map(user, UserDTO.class)));
    }

    /**
     * Cierra la sesión eliminando de la lista blanca el token ingresado
     *
     * @param token Token a eliminar
     */
    public void signOut(String token) {
        String[] authElements = token.split(" ");
        jwtAuthenticationProvider.deleteToken(authElements[1]);
    }

}