package com.tomasdev.akhanta.orders;

import com.tomasdev.akhanta.cart.CartItem;
import com.tomasdev.akhanta.users.Address;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "orders")
public class ShopOrder {

    @Id
    private String shopOrderId;
    private String customerId;
    private String shopId;
    private List<CartItem> items;
    private String paymentMethod;
    private String shippingMethod;
    private Address shippingAddress;
    private Integer status;
    private Double totalPrice;
    private Date createdAt;

    public ShopOrder(String customerId, String shopId, List<CartItem> items, Double totalPrice, Address shippingAddress, String paymentMethod, String shippingMethod) {
        this.customerId = customerId;
        this.shopId = shopId;
        this.items = items;
        this.paymentMethod = paymentMethod;
        this.shippingMethod = shippingMethod;
        this.shippingAddress = shippingAddress;
        this.status = 1;
        this.totalPrice = totalPrice;
        this.createdAt = new Date();
    }
}
