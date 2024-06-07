package com.tomasdev.akhanta.users.customer;

import com.tomasdev.akhanta.auth.PasswordChangeDTO;
import com.tomasdev.akhanta.cart.CartService;
import com.tomasdev.akhanta.exceptions.ServiceException;
import com.tomasdev.akhanta.exceptions.UserExistsException;
import com.tomasdev.akhanta.exceptions.WrongCredentialsException;
import com.tomasdev.akhanta.security.Roles;
import com.tomasdev.akhanta.security.jwt.JwtService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final ModelMapper mapper;
    private final CustomerRepository repository;
    private final CartService cartService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Customer registerCustomer(CustomerDTO customerDTO) {

        if (!customerDTO.getEmail().matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
            throw new WrongCredentialsException("Ingrese una dirección de correo válida");
        }

        Customer customer = mapper.map(customerDTO, Customer.class);

        if (customer.getCustomerId() != null || repository.findByEmail(customer.getEmail()).isPresent()) {
            throw new UserExistsException();
        }

        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setActive(1);
        customer.setRole(Roles.CUSTOMER);
        customer.setUsername(STR."\{customer.getFirstName()} \{customer.getLastName()}");
        Customer savedCustomer = repository.save(customer);
        savedCustomer.setCartId(cartService.createNewCart(savedCustomer.getCustomerId()));

        log.info("[ Registering customer email: {} ]", savedCustomer.getEmail());
        return repository.save(savedCustomer);
    }

    @Override
    public Customer findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(WrongCredentialsException::new);
    }

    @Override
    public void changePassword(PasswordChangeDTO passwordDTO, String jwt) {

        String email = JwtService.extractClaim(jwt, "email");
        Customer customer = findByEmail(email);

        if (!passwordEncoder.matches(passwordDTO.getCurrentPassword(), customer.getPassword())) {
             throw new WrongCredentialsException("Contraseña incorrecta.");
        }

        if (!passwordDTO.getNewPassword().matches(passwordDTO.getRepeatedPassword())) {
            throw new WrongCredentialsException("Las contraseñas no coinciden");
        }

        customer.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));

        repository.save(customer);
    }
}
