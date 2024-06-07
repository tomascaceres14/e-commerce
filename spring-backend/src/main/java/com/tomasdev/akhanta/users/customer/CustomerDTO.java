package com.tomasdev.akhanta.users.customer;

import com.tomasdev.akhanta.users.UserDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class CustomerDTO extends UserDTO {

    private String customerId;
    private String firstName;
    private String lastName;
    private String username;
    private String phoneNumber;

}
