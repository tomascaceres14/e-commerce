package com.tomasdev.akhanta.controller;

import com.tomasdev.akhanta.security.JwtService;
import com.tomasdev.akhanta.service.dto.TokenUserQuery;
import com.tomasdev.akhanta.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/")
@AllArgsConstructor
public class HealthCheck {

    private final JwtService repo;
    @GetMapping("/asd")
    public String healthCheck() {
        return StringUtils.uniqueSequence();
    }

    @GetMapping("/")
    public ResponseEntity<TokenUserQuery> healthCheck(@RequestParam String token) {
        return ResponseEntity.ok(repo.findByTokenWithUser(token));
    }
}
