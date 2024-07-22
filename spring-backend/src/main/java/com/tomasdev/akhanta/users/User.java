package com.tomasdev.akhanta.users;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String email;
    private String password;
    private Integer status;
    private String role;
    private Address address;
    private String firstName;
    private String lastName;
    private String username;
    private String phoneNumber;
    private String cartId;

}

