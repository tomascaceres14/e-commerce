package com.tomasdev.akhanta.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String cellphone_number;
    private Integer active;
    private String password;
    private String role;
}
