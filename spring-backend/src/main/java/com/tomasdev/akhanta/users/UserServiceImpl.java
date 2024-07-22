package com.tomasdev.akhanta.users;

import com.tomasdev.akhanta.auth.PasswordChangeDTO;
import com.tomasdev.akhanta.auth.dto.CustomerRegisterDTO;
import com.tomasdev.akhanta.cart.CartService;
import com.tomasdev.akhanta.exceptions.ResourceNotFoundException;
import com.tomasdev.akhanta.exceptions.UserExistsException;
import com.tomasdev.akhanta.exceptions.WrongCredentialsException;
import com.tomasdev.akhanta.security.Roles;
import com.tomasdev.akhanta.security.jwt.JwtService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final ModelMapper mapper;
    private final CartService cartService;
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(CustomerRegisterDTO customerDTO) {

        if (!customerDTO.getEmail().matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
            throw new WrongCredentialsException("Ingrese una dirección de correo válida");
        }

        User user = mapper.map(customerDTO, User.class);

        if (user.getId() != null || repository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserExistsException();
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(1);
        user.setRole(Roles.CUSTOMER);
        user.setUsername(STR."\{user.getFirstName()} \{user.getLastName()}");
        User savedUser = repository.save(user);
        savedUser.setCartId(new ObjectId(cartService.createNewCart(savedUser.getId())));

        log.info("[ Registering user email: {} ]", savedUser.getEmail());
        return repository.save(savedUser);
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(STR."Cliente '\{email}' no existe."));
    }

    @Override
    public User findById(String id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(STR."Cliente '\{id}' no existe."));
    }

    @Override
    public Page<User> findAll(Integer page, Integer size) {
        PageRequest pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }

    @Override
    public Integer updateStatusById(String id, Integer status) {
        return repository.findAndUpdateStatusById(id, status);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Override
    public void changePassword(PasswordChangeDTO passwordDTO, String jwt) {

        String email = JwtService.extractClaim(jwt, "email");
        User user = findByEmail(email);

        if (!passwordEncoder.matches(passwordDTO.getCurrentPassword(), user.getPassword())) {
            throw new WrongCredentialsException("Contraseña incorrecta.");
        }

        if (!passwordDTO.getNewPassword().matches(passwordDTO.getRepeatedPassword())) {
            throw new WrongCredentialsException("Las contraseñas no coinciden");
        }

        user.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));

        repository.save(user);
    }

    @Override
    public User findUserByEmailAndRole(String email, String role) {
//        MatchOperation matchStage = Aggregation.match(Criteria.where("email").is(email).and("role").is(role));
//        UnionWithOperation unionWithShops = UnionWithOperation.unionWith("shops");
//
//        Aggregation aggregation = Aggregation.newAggregation(
//                matchStage,
//                unionWithShops,
//                Aggregation.match(Criteria.where("email").is(email).and("role").is(role))
//        );
//
//        AggregationResults<User> results = mongoTemplate.aggregate(aggregation, "customers", User.class);
//        if (results.getMappedResults().isEmpty()) throw new ResourceNotFoundException("Usuario");

        return repository.findUserByEmailAndRole(email, role);
    }

    @Override
    public User findUserByIdAndRole(String id, String role) {
//        MatchOperation matchStage = Aggregation.match(Criteria.where("_id").is(id).and("role").is(role));
//        UnionWithOperation unionWithShops = UnionWithOperation.unionWith("shops");
//
//        Aggregation aggregation = Aggregation.newAggregation(
//                matchStage,
//                unionWithShops,
//                Aggregation.match(Criteria.where("_id").is(id).and("role").is(role))
//        );
//
//        AggregationResults<User> results = mongoTemplate.aggregate(aggregation, "customers", User.class);
        return repository.findUserByIdAndRole(id, role);
    }
}
