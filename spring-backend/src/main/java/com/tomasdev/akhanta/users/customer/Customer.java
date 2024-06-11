package com.tomasdev.akhanta.users.customer;

import com.tomasdev.akhanta.users.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "customers")
public class Customer extends User {

    @Id
    private String customerId;
    private String firstName;
    private String lastName;
    private String username;
    private String phoneNumber;
    private String cartId;
    private List<String> ordersList;

}
