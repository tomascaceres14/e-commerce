package com.tomasdev.akhanta.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "articles")
public class Article {
    @Id
    private String id;
    @NotBlank(message = "Title cannot be empty nor null")
    private String title;
    @NotBlank(message = "Content cannot be empty nor null")
    private String content;
    @NotBlank(message = "Date cannot be empty nor null")
    private String creation_date;
    private String image_url;
}
