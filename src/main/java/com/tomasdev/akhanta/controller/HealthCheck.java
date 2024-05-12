package com.tomasdev.akhanta.controller;

import com.tomasdev.akhanta.service.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class HealthCheck {

    private final EmailService emailService;

    @GetMapping("/")
    public ResponseEntity<?> healthCheck() {
        return ResponseEntity.ok().build();
    }
}
