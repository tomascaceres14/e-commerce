package com.tomasdev.akhanta.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserCredentialsDTO {

    @NotBlank(message = "Ingrese su correo electrónico")
    private String email;
    @NotBlank(message = "Ingrese su contraseña")
    private String password;
}
