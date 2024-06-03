package com.tomasdev.akhanta.associate;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "associates")
public class Associate {

    @Id
    private String associateId;
    private String name;
    private String description;
    private List<Link> links;
    private String profile_url;
    private String banner_url;

    @Setter
    @Getter
    @AllArgsConstructor
    public static class Link {
        private String url;
        private String tag;
    }
}
