package com.tomasdev.akhanta.service;

import com.tomasdev.akhanta.model.User;
import com.tomasdev.akhanta.model.dto.ResponseUserDTO;
import com.tomasdev.akhanta.model.dto.UserDTO;
import com.tomasdev.akhanta.service.dto.ChangePasswordDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {

    User registerUser(UserDTO req);
    User findByEmail(String email);
    void changePassword(ChangePasswordDTO passwordDTO, HttpServletRequest request);
}
