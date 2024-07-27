package com.tomasdev.akhanta.users;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "users")
public class User {

    @Id
    private String id;
    private String email;
    private String password;
    private Integer status;
    private List<String> roles;
    private Address address;
    private String firstName;
    private String lastName;
    private String username;
    private String phoneNumber;
    private String cartId;

    public void addRole(String role) {
        this.roles.add(role);
    }

}

