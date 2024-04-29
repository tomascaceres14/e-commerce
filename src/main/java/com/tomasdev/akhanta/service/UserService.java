package com.tomasdev.akhanta.service;

import com.tomasdev.akhanta.model.User;
import com.tomasdev.akhanta.model.dto.ResponseUserDTO;
import com.tomasdev.akhanta.model.dto.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {

    User registerUser(UserDTO req);
    User findByEmail(String email);
}
