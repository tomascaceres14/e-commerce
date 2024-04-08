package com.tomasdev.akhanta.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "activities")
public class Activity {
    @Id
    private String id;
    @NotBlank(message = "Class name cannot be null or empty")
    private String name;
    private List<String> professors;
    private List<daysHoursList> daysHoursList;

    @Data
    @NoArgsConstructor
    static class daysHoursList {
        private List<String> days;
        private String hour;
    }

}
