package com.tomasdev.akhanta.users;

import com.tomasdev.akhanta.auth.PasswordChangeDTO;
import com.tomasdev.akhanta.auth.dto.CustomerRegisterDTO;
import org.springframework.data.domain.Page;

public interface UserService {

    User register(CustomerRegisterDTO req);
    User findByEmail(String email);
    User findById(String id);

    Page<User> findAll(Integer page, Integer size);

    Integer updateStatusById(String id, Integer status);
    void deleteById(String id);

    void changePassword(PasswordChangeDTO passwordDTO, String jwt);

    User findUserByEmailAndRole(String email, String role);
    User findUserByIdAndRole(String id, String role);
}
