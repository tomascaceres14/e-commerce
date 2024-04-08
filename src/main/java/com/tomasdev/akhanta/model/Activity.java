package com.tomasdev.akhanta.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
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
    @NotBlank(message = "Class hour cannot be null or empty")
    private List<String> hours;
    @NotBlank(message = "Class professor cannot be null or empty")
    private List<String> professors;
}
