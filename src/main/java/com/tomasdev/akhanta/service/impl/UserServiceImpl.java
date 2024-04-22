package com.tomasdev.akhanta.service.impl;

import com.tomasdev.akhanta.exceptions.UserExistsException;
import com.tomasdev.akhanta.exceptions.WrongCredentialsException;
import com.tomasdev.akhanta.exceptions.ResourceNotFoundException;
import com.tomasdev.akhanta.model.User;
import com.tomasdev.akhanta.model.dto.ResponseUserDTO;
import com.tomasdev.akhanta.model.dto.UserDTO;
import com.tomasdev.akhanta.repository.UserRepository;
import com.tomasdev.akhanta.security.Roles;
import com.tomasdev.akhanta.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(WrongCredentialsException::new);
    }

    @Override
    public User save(User req){
        return repository.save(req);
    }

    @Override
    public ResponseUserDTO registerUser(UserDTO req) {
        if (!req.getEmail().matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
            throw new WrongCredentialsException();
        }

        User user = mapper.map(req, User.class);

        if ((user.getId() != null && repository.existsById(user.getId())) || repository.findByEmail(user.getEmail()).isPresent()) {
           throw new UserExistsException();
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(1);
        user.setRole(Roles.CUSTOMER);

        User userDB = repository.save(user);

        log.info("Registering user id: {}", userDB.getId());
        return mapper.map(userDB, ResponseUserDTO.class);
    }

    @Override
    public Page<User> findAll(int page) {
        PageRequest pageable = PageRequest.of(page, 10);
        return repository.findAll(pageable);
    }

    @Override
    public User findById(String id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuario"));
    }

    @Override
    public User updateById(String id, User req) {
        User userDB = findById(id);
        mapper.map(req, userDB);
        userDB.setId(id);
        return repository.save(userDB);
    }

    @Override
    public void deleteById(String id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario");
        }
        repository.deleteById(id);
    }
}
