package com.tomasdev.akhanta.user;

import com.tomasdev.akhanta.auth.ChangePasswordDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {

    User registerUser(UserDTO req);
    User findByEmail(String email);
    void changePassword(ChangePasswordDTO passwordDTO, HttpServletRequest request);
}
