package com.tomasdev.akhanta.users.shop;

import com.tomasdev.akhanta.auth.dto.ShopRegisterDTO;
import com.tomasdev.akhanta.home.dto.HomeShopDTO;
import com.tomasdev.akhanta.users.IUser;
import org.springframework.data.domain.Page;

public interface ShopService extends IUser<Shop, ShopRegisterDTO> {
    HomeShopDTO findBySeName(String name);
    Page<HomeShopDTO> findAllShops(int page, int size);
    void addProductById(String id, String productId);
}
