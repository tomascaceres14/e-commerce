package com.tomasdev.akhanta.user;

import com.tomasdev.akhanta.exceptions.ServiceException;
import com.tomasdev.akhanta.exceptions.UserExistsException;
import com.tomasdev.akhanta.exceptions.WrongCredentialsException;
import com.tomasdev.akhanta.security.jwt.JwtService;
import com.tomasdev.akhanta.security.Roles;
import com.tomasdev.akhanta.cart.CartService;
import com.tomasdev.akhanta.auth.ChangePasswordDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final ModelMapper mapper;
    private final UserRepository repository;
    private final CartService cartService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(UserDTO userDTO) {

        if (!userDTO.getEmail().matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
            throw new WrongCredentialsException("Ingrese una dirección de correo válida");
        }

        User user = mapper.map(userDTO, User.class);

        if (user.getUserId() != null || repository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserExistsException();
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(1);
        user.setRole(Roles.CUSTOMER);
        user.setUsername(STR."\{user.getFirstName()} \{user.getLastName()}");
        User savedUser = repository.save(user);
        savedUser.setCartId(new ObjectId(cartService.createNewCart(savedUser.getUserId())));

        log.info("[ Registering user email: {} ]", savedUser.getEmail());
        return repository.save(savedUser);
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(WrongCredentialsException::new);
    }

    @Override
    public void changePassword(ChangePasswordDTO passwordDTO, String jwt) {

        String email = JwtService.extractClaim(jwt, "email");
        User user = findByEmail(email);

        if (!passwordEncoder.matches(passwordDTO.getCurrentPassword(), user.getPassword())) {
             throw new WrongCredentialsException("Contraseña incorrecta.");
        }

        if (!passwordDTO.getNewPassword().matches(passwordDTO.getRepeatedPassword())) {
            throw new ServiceException("Las contraseñas no coinciden");
        }

        user.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));

        repository.save(user);
    }
}
