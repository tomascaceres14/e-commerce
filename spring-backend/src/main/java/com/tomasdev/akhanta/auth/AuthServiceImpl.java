package com.tomasdev.akhanta.auth;

import com.tomasdev.akhanta.exceptions.ServiceException;
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

/**
 * Servicio encargado del logueo de un usuario
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public JwtResponseDTO registerCustomer(CustomerRegisterDTO customerDTO) {

        Customer customer = customerService.register(customerDTO);

        String accessToken = jwtService.buildCustomerAccessToken(customer);
        String refreshToken = jwtService.buildRefreshToken(customer);

        return new JwtResponseDTO(accessToken, refreshToken);
    }

    @Override
    public JwtResponseDTO registerShop(ShopRegisterDTO shopDTO) {

        Shop shop = shopService.register(shopDTO);

        String accessToken = jwtService.buildShopAccessToken(shop);
        String refreshToken = jwtService.buildRefreshToken(shop);

        return new JwtResponseDTO(accessToken, refreshToken);
    }

    @Override
    public JwtResponseDTO logIn(UserCredentialsDTO credentials, String role) {
        User user = userService.findUserByEmailAndRol(credentials.getEmail(), role);

        if (!passwordEncoder.matches(credentials.getPassword(), user.getPassword())) {
            throw new WrongCredentialsException();
        }

        return jwtService.grantAccess(user);
    }

    /**
     * Devuelve un dto con el jwt de acceso y de refresco del usuario dadas unas credenciales
     * @param credentials Credenciales de acceso
     * @return Dto con el jwt del usuario si las credenciales son validas
     */
    @Override
    public JwtResponseDTO customerLogIn(UserCredentialsDTO credentials) {

        Customer customer = customerService.findByEmail(credentials.getEmail());

        if (!passwordEncoder.matches(credentials.getPassword(), customer.getPassword())) {
            throw new WrongCredentialsException();
        }

        String accessToken = jwtService.buildCustomerAccessToken(customer);
        String refreshToken = jwtService.buildRefreshToken(customer);

        return new JwtResponseDTO(accessToken, refreshToken);
    }

    /**
     * Cierra la sesión eliminando de la lista blanca el token ingresado
     * @param token Token a eliminar
     */
    public void signOut(String token) {
        jwtService.revokeToken(token);
    }

    public JwtResponseDTO refreshAccessToken(String refreshToken) {
        String userEmail = JwtService.extractClaim(refreshToken, "email");
        String role = JwtService.extractClaim(refreshToken, "role");

        User user = userService.findUserByEmailAndRol(userEmail, role);
        String accessToken = jwtService.buildCustomerAccessToken(user);
        String newRefreshToken = jwtService.buildRefreshToken(user);

        return new JwtResponseDTO(accessToken, newRefreshToken);
    }
}