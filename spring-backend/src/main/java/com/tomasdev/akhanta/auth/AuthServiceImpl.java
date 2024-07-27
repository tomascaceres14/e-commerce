package com.tomasdev.akhanta.auth;

import com.tomasdev.akhanta.exceptions.WrongCredentialsException;
import com.tomasdev.akhanta.security.jwt.JwtResponseDTO;
import com.tomasdev.akhanta.security.jwt.JwtService;
import com.tomasdev.akhanta.auth.dto.CustomerRegisterDTO;
import com.tomasdev.akhanta.users.User;
import com.tomasdev.akhanta.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public JwtResponseDTO register(CustomerRegisterDTO customerDTO) {
        User user = userService.register(customerDTO);
        return jwtService.grantAccess(user);
    }

    public JwtResponseDTO logIn(LogInCredentialsDTO credentials, String role) {

        User user = userService.findUserByEmailAndRole(credentials.getEmail(), role);

        if (!passwordEncoder.matches(credentials.getPassword(), user.getPassword())) {
            throw new WrongCredentialsException();
        }

        return jwtService.grantAccess(user);
    }

    @Override
    public JwtResponseDTO adminLogIn(LogInCredentialsDTO credentials) {

        User adminUser = userService.findByEmail(credentials.getEmail());

        if (!passwordEncoder.matches(credentials.getPassword(), adminUser.getPassword())) {
            throw new WrongCredentialsException();
        }

        return jwtService.grantAccess(adminUser);
    }

    public void signOut(String token) {
        jwtService.revokeToken(token);
    }

    public JwtResponseDTO refreshAccessToken(String refreshToken) {

        String userId = JwtService.extractClaim(refreshToken, "id");
        String role = JwtService.extractClaim(refreshToken, "role");
        User user = userService.findUserByIdAndRole(userId, role);

        return jwtService.grantAccess(user);
    }
}