package com.tomasdev.akhanta.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class TestController {
    @GetMapping
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Cambalache working");
    }
}
