package com.tomasdev.akhanta.auth;

import com.tomasdev.akhanta.auth.dto.UserRegisterDTO;
import com.tomasdev.akhanta.exceptions.WrongCredentialsException;
import com.tomasdev.akhanta.security.jwt.JwtResponseDTO;
import com.tomasdev.akhanta.security.jwt.JwtService;
import com.tomasdev.akhanta.users.User;
import com.tomasdev.akhanta.users.UserService;
import com.tomasdev.akhanta.users.customer.Customer;
import com.tomasdev.akhanta.auth.dto.CustomerRegisterDTO;
import com.tomasdev.akhanta.users.customer.CustomerService;
import com.tomasdev.akhanta.users.shop.Shop;
import com.tomasdev.akhanta.auth.dto.ShopRegisterDTO;
import com.tomasdev.akhanta.users.shop.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final CustomerService customerService;
    private final ShopService shopService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public JwtResponseDTO registerCustomer(CustomerRegisterDTO customerDTO) {
        Customer customer = customerService.register(customerDTO);
        return jwtService.grantAccess(customer, customer.getRole());
    }

    @Override
    public JwtResponseDTO registerShop(ShopRegisterDTO shopDTO) {
        Shop shop = shopService.register(shopDTO);
        return jwtService.grantAccess(shop, shop.getRole());
    }

    public JwtResponseDTO logIn(LogInCredentialsDTO credentials, String role) {

        User user = userService.findUserByEmailAndRole(credentials.getEmail(), role);

        if (!passwordEncoder.matches(credentials.getPassword(), user.getPassword())) {
            throw new WrongCredentialsException();
        }

        return jwtService.grantAccess(user, role);
    }

    @Override
    public JwtResponseDTO customerLogIn(LogInCredentialsDTO credentials) {

        Customer customer = customerService.findByEmail(credentials.getEmail());

        if (!passwordEncoder.matches(credentials.getPassword(), customer.getPassword())) {
            throw new WrongCredentialsException();
        }

        return jwtService.grantAccess(customer, customer.getRole());
    }

    public void signOut(String token) {
        jwtService.revokeToken(token);
    }

    public JwtResponseDTO refreshAccessToken(String refreshToken) {

        String userId = JwtService.extractClaim(refreshToken, "id");
        String role = JwtService.extractClaim(refreshToken, "role");
        User user = userService.findUserByIdAndRole(userId, role);

        return jwtService.grantAccess(user, role);
    }
}