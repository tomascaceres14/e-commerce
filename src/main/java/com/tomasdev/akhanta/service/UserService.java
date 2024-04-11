package com.tomasdev.akhanta.service;

import com.tomasdev.akhanta.model.User;
import com.tomasdev.akhanta.model.dto.ResponseUserDTO;
import com.tomasdev.akhanta.model.dto.UserDTO;

public interface UserService extends iService<User> {

    User findByEmail(String email);
    ResponseUserDTO registerUser(UserDTO req);
}
