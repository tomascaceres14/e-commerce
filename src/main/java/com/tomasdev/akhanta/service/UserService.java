package com.tomasdev.akhanta.service;

import com.tomasdev.akhanta.model.User;

public interface UserService extends iService<User> {

    User findByEmail(String email);
}
