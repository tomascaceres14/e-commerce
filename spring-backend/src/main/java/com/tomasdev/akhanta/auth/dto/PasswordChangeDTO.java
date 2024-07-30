package com.tomasdev.akhanta.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordChangeDTO {

    @NotBlank(message = "Por favor, introduzca su contraseña.")
    private String currentPassword;
    @NotBlank(message = "Por favor, repita su contraseña.")
    private String repeatedPassword;
    @NotBlank(message = "Por favor, introduzca su nueva contraseña.")
    private String newPassword;

}
