package com.tomasdev.akhanta.users;

import lombok.Data;

@Data
public class User {

    private String email;
    private String password;
    private Integer status;
    private String role;
    private Address address;

}

