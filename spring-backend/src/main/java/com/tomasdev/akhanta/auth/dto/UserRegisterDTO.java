package com.tomasdev.akhanta.auth.dto;

import com.tomasdev.akhanta.users.Address;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class UserRegisterDTO {

    @NotBlank(message = "Por favor introduzca un correo electrónico.")
    private String email;
    @NotBlank(message = "Por favor introduzca una contraseña.")
    private String password;
    private Address address;

}
