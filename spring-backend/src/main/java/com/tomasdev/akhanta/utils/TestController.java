package com.tomasdev.akhanta.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    @GetMapping
    public Object test(@RequestParam String data, @RequestParam String data2) {
        return "working";
    }
}
