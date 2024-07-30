package com.tomasdev.akhanta.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LogInCredentialsDTO {

    @NotBlank(message = "Por favor, ingrese un correo electrónico.")
    private String email;
    @NotBlank(message = "Por favor, ingrese una contraseña.")
    private String password;
}
