package com.tomasdev.akhanta.users.shop;

import com.tomasdev.akhanta.auth.PasswordChangeDTO;
import com.tomasdev.akhanta.auth.dto.ShopRegisterDTO;
import com.tomasdev.akhanta.home.dto.HomeShopDTO;

public interface ShopService {
    Shop registerShop(ShopRegisterDTO req);
    Shop findByEmail(String email);
    HomeShopDTO findBySeName(String name);
    void changePassword(PasswordChangeDTO passwordDTO, String jwt);
}
