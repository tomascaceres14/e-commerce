package com.tomasdev.akhanta.controller;

import com.tomasdev.akhanta.service.UserService;
import com.tomasdev.akhanta.service.dto.ChangePasswordDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @RequestMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO passwordDTO, HttpServletRequest request) {
        service.changePassword(passwordDTO, request);
        return ResponseEntity.status(HttpStatus.SC_RESET_CONTENT).build();
    }

}
