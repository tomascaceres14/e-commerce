package com.tomasdev.akhanta.orders;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers/orders")
@RequiredArgsConstructor
public class CustomerOrderController {

    private final ShopOrderService service;

    @GetMapping
    public ResponseEntity<Page<ShopOrder>> findAllOrders(@RequestParam(required = false, defaultValue = "") String shopId,
                                                         @RequestParam(required = false, defaultValue = "0") Integer page,
                                                         @RequestHeader(name = HttpHeaders.AUTHORIZATION) String jwt) {
        return ResponseEntity.ok(service.findAllOrdersByCustomer(jwt, shopId, page));
    }

    @PostMapping
    public ResponseEntity<List<ShopOrder>> createOrder(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String jwt) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createOrder(jwt));
    }
}
