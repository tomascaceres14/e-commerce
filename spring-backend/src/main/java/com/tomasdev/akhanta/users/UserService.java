package com.tomasdev.akhanta.users;

import com.tomasdev.akhanta.auth.PasswordChangeDTO;

public interface UserService {

    User registerUser(UserDTO req);
    User findByEmail(String email);
    void changePassword(PasswordChangeDTO passwordDTO, String jwt);
}
