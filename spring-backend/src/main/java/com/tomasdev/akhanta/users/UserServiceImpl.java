package com.tomasdev.akhanta.users;

import com.tomasdev.akhanta.exceptions.ServiceException;
import com.tomasdev.akhanta.exceptions.WrongCredentialsException;
import com.tomasdev.akhanta.security.jwt.JwtService;
import com.tomasdev.akhanta.auth.PasswordChangeDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    /*
    @Override
    public User registerUser(UserDTO userDTO) {

        if (!userDTO.getEmail().matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
            throw new WrongCredentialsException("Ingrese una dirección de correo válida");
        }

        User customer = mapper.map(userDTO, User.class);

        if (customer.getUserId() != null || repository.findByEmail(customer.getEmail()).isPresent()) {
            throw new UserExistsException();
        }

        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setActive(1);
        customer.setRole(Roles.CUSTOMER);
        customer.setUsername(STR."\{customer.getFirstName()} \{customer.getLastName()}");
        User savedCustomer = repository.save(customer);
        savedCustomer.setCartId(new ObjectId(cartService.createNewCart(savedCustomer.getUserId())));

        log.info("[ Registering customer email: {} ]", savedCustomer.getEmail());
        return repository.save(savedCustomer);
    }
     */

    @Override
    public User registerUser(UserDTO req) {
        return null;
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(WrongCredentialsException::new);
    }

    @Override
    public void changePassword(PasswordChangeDTO passwordDTO, String jwt) {

        String email = JwtService.extractClaim(jwt, "email");
        User customer = findByEmail(email);

        if (!passwordEncoder.matches(passwordDTO.getCurrentPassword(), customer.getPassword())) {
             throw new WrongCredentialsException("Contraseña incorrecta.");
        }

        if (!passwordDTO.getNewPassword().matches(passwordDTO.getRepeatedPassword())) {
            throw new ServiceException("Las contraseñas no coinciden");
        }

        customer.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));

        repository.save(customer);
    }
}
