package com.tomasdev.akhanta.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "calendar")
public class CalendarDay {
    private String id;
    private String day;
    private List<String> activities;
}
