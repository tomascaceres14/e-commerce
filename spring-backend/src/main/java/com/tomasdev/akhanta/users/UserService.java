package com.tomasdev.akhanta.users;

import com.tomasdev.akhanta.auth.dto.PasswordChangeDTO;
import com.tomasdev.akhanta.auth.dto.CustomerRegisterDTO;
import org.springframework.data.domain.Page;

public interface UserService {

    User register(CustomerRegisterDTO req);
    User findByEmail(String email);
    User findById(String id);
    Page<User> findAll(Integer page, Integer size);
    Integer updateStatusById(String id, Integer status);
    Integer updateRoleById(String id, String role);
    void changePassword(PasswordChangeDTO passwordDTO, String jwt);
}
