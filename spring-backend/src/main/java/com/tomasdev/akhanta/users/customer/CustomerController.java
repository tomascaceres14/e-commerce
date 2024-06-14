package com.tomasdev.akhanta.users.customer;

import com.tomasdev.akhanta.auth.PasswordChangeDTO;
import com.tomasdev.akhanta.orders.ShopOrder;
import com.tomasdev.akhanta.orders.ShopOrderService;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;
    private final ShopOrderService orderService;

    @PostMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeDTO passwordDTO,
                                            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String jwt) {
        service.changePassword(passwordDTO, jwt);
        return ResponseEntity.status(HttpStatus.SC_RESET_CONTENT).build();
    }

    @GetMapping("/orders")
    public ResponseEntity<Page<ShopOrder>> findAllOrders(@RequestParam(required = false, defaultValue = "") String shopId,
                                                         @RequestParam(required = false, defaultValue = "0") Integer page,
                                                         @RequestHeader(name = HttpHeaders.AUTHORIZATION) String jwt) {
        return ResponseEntity.ok(orderService.findAllOrdersByCustomer(jwt, shopId, page));
    }

    @PostMapping("/orders")
    public ResponseEntity<List<ShopOrder>> createOrder(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String jwt) {
        return ResponseEntity.status(org.springframework.http.HttpStatus.CREATED).body(orderService.createOrder(jwt));
    }
}
