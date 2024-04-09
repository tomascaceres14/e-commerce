package com.tomasdev.akhanta.controller;

import com.tomasdev.akhanta.model.Activity;
import com.tomasdev.akhanta.service.impl.ActivityServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/activities")
public class ActivityController {

    private ActivityServiceImpl service;

    @GetMapping("")
    ResponseEntity<Page<Activity>> findAll(@RequestParam(required = false, defaultValue = "0") Integer page) {
        return new ResponseEntity<>(service.findAll(page), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<Activity> save(@Valid @RequestBody Activity activity) {
        return new ResponseEntity<>(service.save(activity), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    ResponseEntity<Activity> findById(@PathVariable String id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

}
