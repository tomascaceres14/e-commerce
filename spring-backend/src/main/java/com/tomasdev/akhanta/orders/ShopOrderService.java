package com.tomasdev.akhanta.orders;

import org.springframework.data.domain.Page;

import java.util.List;

public interface ShopOrderService {
    List<ShopOrder> createOrder(String jwt);
    Page<ShopOrder> filterOrders(String customerId, String shopId, int page, int size);
    Page<ShopOrder> findAllOrdersByShop(String jwt, String customerId, Integer page);
    Page<ShopOrder> findAllOrdersByCustomer(String jwt, String shopId, Integer page);

    String deleteOrderById(String id);
}
