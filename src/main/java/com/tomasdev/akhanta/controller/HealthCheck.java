package com.tomasdev.akhanta.controller;

import com.tomasdev.akhanta.security.JwtService;
import com.tomasdev.akhanta.service.EmailService;
import com.tomasdev.akhanta.service.dto.TokenUserQuery;
import com.tomasdev.akhanta.utils.StringUtils;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/")
@AllArgsConstructor
public class HealthCheck {

    private final EmailService emailService;

    @GetMapping("/")
    public void healthCheck() throws MessagingException, IOException {
        emailService.sendEmail("anacosta21@icloud.com", "Expandiendo conocimientos");
    }
}
