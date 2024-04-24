package com.tomasdev.akhanta.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
public class ProductRequestDTO {

    private String productId;
    private String title;
    private String description;
    private Integer quantity;
    private Float price;
    private List<String> categories;
    private List<String> images;

}
