package com.tomasdev.akhanta.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Data
@NoArgsConstructor
@Document(collection = "articles")
public class Article {
    @Id
    private String articleId;
    private String title;
    private String seTitle;
    private String content;
    private Date creation_date;
    private String image_url;
}
