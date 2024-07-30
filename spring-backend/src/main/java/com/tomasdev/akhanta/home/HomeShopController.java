package com.tomasdev.akhanta.home;

import com.tomasdev.akhanta.home.dto.HomeShopDTO;
import com.tomasdev.akhanta.shop.ShopService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/home/shops")
@AllArgsConstructor
public class HomeShopController {

    private final ShopService shopService;

    @GetMapping
    public ResponseEntity<Page<HomeShopDTO>> findAllShops(@RequestParam(required = false, defaultValue = "0") int page,
                                                          @RequestParam(required = false, defaultValue = "1") int size) {
        return ResponseEntity.ok(shopService.findAllShops(page, size));
    }

    @GetMapping("/{seName}")
    public ResponseEntity<HomeShopDTO> findShopBySeName(@PathVariable String seName) {
        return ResponseEntity.ok(shopService.findBySeName(seName));
    }
}
