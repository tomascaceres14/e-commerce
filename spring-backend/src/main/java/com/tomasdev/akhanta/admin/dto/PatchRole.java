package com.tomasdev.akhanta.admin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PatchRole {
    @NotBlank(message = "Por favor, introduzca un rol.")
    private String role;
}
