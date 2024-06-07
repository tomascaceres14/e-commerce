package com.tomasdev.akhanta.users;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class UserDTO {

    @NotBlank
    private String email;
    @NotBlank
    private String password;
    private Integer active;
    private String role;
    private Address address;

}
