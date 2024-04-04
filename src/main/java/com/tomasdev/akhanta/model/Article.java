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
    @NotBlank(message = "Article title can't be empty nor null")
    private String title;
    @NotBlank(message = "Article content can't be empty nor null")
    private String content;
    @NotBlank(message = "Article date can't be empty nor null")
    private String creation_date;
    private String image_url;
}
