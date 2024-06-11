package com.tomasdev.akhanta.utils;

import com.tomasdev.akhanta.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final UserService userService;
    @GetMapping
    public Object test(@RequestParam String data, @RequestParam String data2) {
        return userService.findUserByEmailAndRole(data, data2);
    }
}
