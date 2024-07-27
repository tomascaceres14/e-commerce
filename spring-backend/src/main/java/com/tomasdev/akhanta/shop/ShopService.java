package com.tomasdev.akhanta.shop;

import com.tomasdev.akhanta.home.dto.HomeShopDTO;
import com.tomasdev.akhanta.shop.dto.CreateShopDTO;
import org.springframework.data.domain.Page;

public interface ShopService {
    HomeShopDTO findBySeName(String name);
    Page<HomeShopDTO> findAllShops(int page, int size);
    Shop saveShop(CreateShopDTO shop);
    void addProductById(String id, String productId);
}
