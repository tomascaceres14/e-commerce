package com.tomasdev.akhanta.auth.dto;

import com.tomasdev.akhanta.users.Address;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class CustomerRegisterDTO {

    @NotBlank(message = "Por favor, introduzca un nombre.")
    private String firstName;
    @NotBlank(message = "Por favor, introduzca un apellido.")
    private String lastName;
    @NotBlank(message = "Por favor, introduzca un correo electrónico.")
    private String email;
    @NotBlank(message = "Por favor, introduzca una contraseña.")
    private String password;
    @NotBlank(message = "Por favor, introduzca un nombre de usuario.")
    private String username;
    @NotBlank(message = "Por favor, introduzca un número telefónico.")
    private String phoneNumber;
    private Address address;
}
