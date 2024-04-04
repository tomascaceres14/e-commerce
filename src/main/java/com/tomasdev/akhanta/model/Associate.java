package com.tomasdev.akhanta.model;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "associates")
public class Associate {

    @Id
    private String id;
    @NotBlank(message = "Associate name can't be empty nor null")
    private String name;
    @NotBlank(message = "Associate description can't be empty nor null")
    private String description;
    private List<Link> links;
    private String profile_url;
    private String banner_url;

    @Setter
    @Getter
    @AllArgsConstructor
    static class Link {
        private String url;
        private String tag;
    }
}
