package com.tomasdev.akhanta.service;

import com.tomasdev.akhanta.model.User;
import com.tomasdev.akhanta.model.dto.ResponseUserDTO;
import com.tomasdev.akhanta.model.dto.UserDTO;

public interface UserService {

    ResponseUserDTO registerUser(UserDTO req);
    User findByEmail(String email);

}
