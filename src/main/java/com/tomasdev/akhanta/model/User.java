package com.tomasdev.akhanta.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@Document(collection = "users")
public class User {

    @Id
    private String userId;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String cellphone_number;
    private Integer active;
    private String password;
    private String role;
    private List<Token> tokensList;
    private List<String> cart;
    private List<ShoppingOrder> ordersList;

    public Optional<Token> getToken(String token) {
        return getTokensList().stream().filter(jwt -> token.equals(jwt.getToken())).findAny();
    }

    public void deleteToken(String token) {
        getTokensList().remove(getToken(token).orElse(null));
    }
}
