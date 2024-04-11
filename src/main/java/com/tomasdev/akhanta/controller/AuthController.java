package com.tomasdev.akhanta.controller;

import com.tomasdev.akhanta.model.dto.UserDTO;
import com.tomasdev.akhanta.model.dto.ResponseCustomerDTO;
import com.tomasdev.akhanta.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserServiceImpl service;
    private ModelMapper mapper;

    @PostMapping("/register")
    public ResponseEntity<ResponseCustomerDTO> save(@RequestBody UserDTO customer) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.map(service.save(customer), ResponseCustomerDTO.class));
    }
}
