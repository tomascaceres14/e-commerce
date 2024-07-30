package com.tomasdev.akhanta.auth;

import com.tomasdev.akhanta.auth.dto.LogInCredentialsDTO;
import com.tomasdev.akhanta.exceptions.WrongCredentialsException;
import com.tomasdev.akhanta.security.jwt.JwtResponseDTO;
import com.tomasdev.akhanta.security.jwt.JwtService;
import com.tomasdev.akhanta.auth.dto.CustomerRegisterDTO;
import com.tomasdev.akhanta.users.User;
import com.tomasdev.akhanta.users.UserRepository;
import com.tomasdev.akhanta.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public JwtResponseDTO registerUser(CustomerRegisterDTO customerDTO) {
        User user = userService.register(customerDTO);
        return jwtService.grantAccess(user);
    }

    public JwtResponseDTO logIn(LogInCredentialsDTO credentials) {

        User user = userRepository.findByEmail(credentials.getEmail()).orElseThrow(WrongCredentialsException::new);

        if (!passwordEncoder.matches(credentials.getPassword(), user.getPassword())) {
            throw new WrongCredentialsException();
        }

        return jwtService.grantAccess(user);
    }

    public void signOut(String token) {
        jwtService.revokeToken(token);
    }

    public JwtResponseDTO refreshAccessToken(String refreshToken) {

        String userId = JwtService.extractClaim(refreshToken, "id");
        User user = userService.findById(userId);

        return jwtService.grantAccess(user);
    }
}