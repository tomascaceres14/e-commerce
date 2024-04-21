package com.tomasdev.akhanta.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "articles")
public class Article {
    @Id
    private String articleId;
    private String title;
    private String content;
    private String creation_date;
    private String image_url;
}
