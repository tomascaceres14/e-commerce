package com.tomasdev.akhanta.users.shop;

import com.tomasdev.akhanta.auth.PasswordChangeDTO;
import com.tomasdev.akhanta.auth.dto.ShopRegisterDTO;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shops")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService service;

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeDTO passwordDTO,
                                            @RequestHeader(name = HttpHeaders.AUTHORIZATION) String jwt) {
        service.changePassword(passwordDTO, jwt);
        return ResponseEntity.status(HttpStatus.SC_RESET_CONTENT).build();
    }

}
