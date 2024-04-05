package com.tomasdev.akhanta.controller;

import com.tomasdev.akhanta.model.Associate;
import com.tomasdev.akhanta.service.impl.AssociateServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/associates")
public class AssociateController {

    private AssociateServiceImpl service;

    @GetMapping("")
    ResponseEntity<Page<Associate>> findAll(@RequestParam(required = false, defaultValue = "0") Integer page) {
        return new ResponseEntity<>(service.findAll(page), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<Associate> findById(@PathVariable String id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PostMapping("")
    ResponseEntity<Associate> save(@Valid @RequestBody Associate associate) {
        return new ResponseEntity<>(service.save(associate), HttpStatus.CREATED);
    }
}
