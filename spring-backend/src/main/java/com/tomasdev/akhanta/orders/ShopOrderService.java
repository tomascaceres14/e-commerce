package com.tomasdev.akhanta.orders;

import java.util.List;

public interface ShopOrderService {
    List<ShopOrder> createOrder(String jwt);
    List<ShopOrder> findAllOrders(Integer page);
}
