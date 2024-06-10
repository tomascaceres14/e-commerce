package com.tomasdev.akhanta.users.shop;

import com.tomasdev.akhanta.auth.dto.ShopRegisterDTO;
import com.tomasdev.akhanta.home.dto.HomeShopDTO;
import com.tomasdev.akhanta.users.IUser;

public interface ShopService extends IUser<Shop, ShopRegisterDTO> {
    HomeShopDTO findBySeName(String name);
}
