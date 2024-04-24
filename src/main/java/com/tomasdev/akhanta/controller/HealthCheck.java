package com.tomasdev.akhanta.controller;

import com.tomasdev.akhanta.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/")
@AllArgsConstructor
public class HealthCheck {

    @GetMapping("/")
    public String healthCheck() {
        return StringUtils.uniqueSequence();
    }
}
