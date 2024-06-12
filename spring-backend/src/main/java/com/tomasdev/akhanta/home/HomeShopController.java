package com.tomasdev.akhanta.home;

import com.tomasdev.akhanta.home.dto.HomeShopDTO;
import com.tomasdev.akhanta.users.shop.ShopService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/home/shops")
@AllArgsConstructor
public class HomeShopController {

    private final ShopService shopService;

    @GetMapping
    public ResponseEntity<HomeShopDTO> findAllShops(@RequestParam(required = false, defaultValue = "0") int page) {
        return ResponseEntity.ok(shopService.findAllShops(page));
    }

    @GetMapping("/{seName}")
    public ResponseEntity<HomeShopDTO> findShopBySeName(@PathVariable String seName) {
        return ResponseEntity.ok(shopService.findBySeName(seName));
    }
}
