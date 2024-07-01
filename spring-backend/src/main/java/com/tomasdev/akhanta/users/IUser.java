package com.tomasdev.akhanta.users;

import com.tomasdev.akhanta.auth.PasswordChangeDTO;
import org.springframework.data.domain.Page;

public interface IUser<T, U> {

    T register(U req);
    T findByEmail(String email);
    T findById(String id);

    Page<T> findAll(Integer page, Integer size);

    void deleteById(String id);

    void changePassword(PasswordChangeDTO passwordDTO, String jwt);

}
