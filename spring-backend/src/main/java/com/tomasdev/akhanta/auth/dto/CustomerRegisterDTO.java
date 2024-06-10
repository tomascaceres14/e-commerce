package com.tomasdev.akhanta.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class CustomerRegisterDTO extends UserRegisterDTO {

    @NotBlank(message = "El nombre es obligatorio.")
    private String firstName;
    @NotBlank(message = "El apellido es obligatorio.")
    private String lastName;
    @NotBlank(message = "Por favor introduzca un nombre de usuario.")
    private String username;
    @NotBlank(message = "Por favor introduzca un número telefónico.")
    private String phoneNumber;

}
