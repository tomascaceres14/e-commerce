package com.tomasdev.akhanta.model.dto;

import com.tomasdev.akhanta.model.Associate;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class AssociateRequestDTO {

    @NotBlank(message = "Ingrese un nombre.")
    private String name;
    @NotBlank(message = "Ingrese una descripción.")
    private String description;
    private List<Link> links;

    @Setter
    @Getter
    @AllArgsConstructor
    static class Link {
        @NotBlank(message = "Ingrese una url.")
        private String url;
        @NotBlank(message = "Ingrese un tag correspondiente.\nOpciones validas: [INSTAGRAM, TELEGRAM, WHATSAPP, FACEBOOK]")
        private String tag;
    }
    
}
