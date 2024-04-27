package com.tomasdev.akhanta.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token {

    @Id
    public String id;

    public String token;

    public boolean revoked;

    public boolean expired;
}
