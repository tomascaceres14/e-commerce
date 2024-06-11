package com.tomasdev.akhanta.home;

import com.tomasdev.akhanta.home.dto.HomeShopDTO;
import com.tomasdev.akhanta.product.ProductService;
import com.tomasdev.akhanta.users.shop.ShopService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/home/shops")
@AllArgsConstructor
public class HomeShopController {

    private final ShopService shopService;

    @GetMapping("/{seName}")
    public ResponseEntity<HomeShopDTO> findShopBySeName(@PathVariable String seName) {
        return ResponseEntity.ok(shopService.findBySeName(seName));
    }

    @GetMapping("/{seName}/products")
    public ResponseEntity<HomeShopDTO> findProductsByShop(@PathVariable String seName) {
        return null;
    }
}
