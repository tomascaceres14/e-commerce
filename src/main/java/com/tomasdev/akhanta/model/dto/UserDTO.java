package com.tomasdev.akhanta.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String cellphone_number;
    private String password;
}
