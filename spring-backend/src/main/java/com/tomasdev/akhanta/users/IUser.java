package com.tomasdev.akhanta.users;

import com.tomasdev.akhanta.auth.PasswordChangeDTO;

public interface IUser<T, U> {

    T register(U req);
    T findByEmail(String email);
    void changePassword(PasswordChangeDTO passwordDTO, String jwt);

}
