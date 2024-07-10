package com.tomasdev.akhanta.users;

import lombok.Data;

@Data
public class Address {

    private String street;
    private String apartmentNumber;
    private String city;
    private String country;
    private String zipCode;
}
