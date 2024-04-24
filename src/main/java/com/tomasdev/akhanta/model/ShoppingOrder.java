package com.tomasdev.akhanta.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document( value = "shopping_orders")
public class ShoppingOrder {

    @Id
    private String shoppingOrderId;
    private Date orderedAt;
    private List<String> productsList;
    private Float payAmount;
    private Float discountAmount;
    private String orderStatus;
    private String paymentMethod;
}
