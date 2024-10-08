package com.tomasdev.akhanta.shop;

import com.tomasdev.akhanta.auth.dto.ShopRegisterDTO;
import com.tomasdev.akhanta.home.dto.HomeShopDTO;
import org.springframework.data.domain.Page;

public interface ShopService {
    HomeShopDTO findBySeName(String name);
    Page<HomeShopDTO> findAllShops(int page, int size);
    Shop saveShop(ShopRegisterDTO shop);
    void addProductById(String id, String productId);
}
