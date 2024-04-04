package com.tomasdev.akhanta.controller;

import com.tomasdev.akhanta.repository.AssociateRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class HealthCheck {

    private final AssociateRepository repo;

    @GetMapping("/")
    public String healthCheck() {
        return "Akhanta API Working";
    }
}
