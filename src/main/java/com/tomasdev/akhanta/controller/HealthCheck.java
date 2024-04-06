package com.tomasdev.akhanta.controller;

import com.tomasdev.akhanta.utils.SequenceGenerator;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class HealthCheck {

    @GetMapping("/")
    public String healthCheck() {
        return SequenceGenerator.uniqueSequence();
    }
}
