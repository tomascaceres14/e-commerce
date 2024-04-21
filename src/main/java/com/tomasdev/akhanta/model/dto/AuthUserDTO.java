package com.tomasdev.akhanta.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuthUserDTO {

    @NotBlank(message = "Ingrese su correo electrónico")
    private String email;
    @NotBlank(message = "Ingrese su contraseña")
    private String password;
}
