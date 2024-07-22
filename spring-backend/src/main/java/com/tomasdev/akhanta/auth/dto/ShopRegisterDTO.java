package com.tomasdev.akhanta.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
public class ShopRegisterDTO extends UserRegisterDTO {

    @NotBlank(message = "El nombre del shop es obligatorio.")
    private String name;

}
