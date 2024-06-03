package com.tomasdev.akhanta.user;

import lombok.Data;

@Data
public class Address {

    private String street;
    private String apartmentNumber;
    private String neighbour;
    private String city;
    private String country;
    private String postalCode;
    private boolean isApartment;
}
