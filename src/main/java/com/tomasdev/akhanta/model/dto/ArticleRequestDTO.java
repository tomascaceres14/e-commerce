package com.tomasdev.akhanta.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor

public class ArticleRequestDTO {

    @NotBlank(message = "Ingrese un título.")
    private String title;
    @NotBlank(message = "Ingrese un contenido.")
    private String content;

}
