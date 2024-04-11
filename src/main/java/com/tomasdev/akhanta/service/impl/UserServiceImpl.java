package com.tomasdev.akhanta.service.impl;

import com.tomasdev.akhanta.exceptions.CustomerExistsException;
import com.tomasdev.akhanta.exceptions.EmailValidationException;
import com.tomasdev.akhanta.exceptions.ResourceNotFoundException;
import com.tomasdev.akhanta.model.User;
import com.tomasdev.akhanta.repository.UserRepository;
import com.tomasdev.akhanta.security.Roles;
import com.tomasdev.akhanta.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Usuario"));
    }

    @Override
    public UserDTO save(User req) {
        if (!req.getEmail().matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
            throw new EmailValidationException();
        }

        if (repository.findById(req.getUserId()).isPresent() || repository.findByEmail(req.getEmail()).isPresent()) {
            throw new CustomerExistsException();
        }

        req.setPassword(passwordEncoder.encode(req.getPassword()));
        req.setActive(1);
        req.setRole(Roles.CUSTOMER);
        repository.save(req);

        return new ResponseCustomerDto(passwordGenerated);
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
        userDB.setUserId(id);
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
