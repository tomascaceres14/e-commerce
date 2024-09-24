package com.tomasdev.akhanta.users;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Address {
    @NotBlank(message = "Por favor, introduzca el nombre de calle")
    private String street;
    @NotBlank(message = "Por favor, introduzca el número de calle")
    private String number;
    private String apartmentNumber;
    private String city;
    private String country;
    private String zipCode;
}
