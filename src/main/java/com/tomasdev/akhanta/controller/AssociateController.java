package com.tomasdev.akhanta.controller;

import com.amazonaws.services.s3.AmazonS3Client;
import com.tomasdev.akhanta.model.Associate;
import com.tomasdev.akhanta.service.impl.AmazonS3ServiceImpl;
import com.tomasdev.akhanta.service.impl.AssociateServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@AllArgsConstructor
@RequestMapping("/home/associates")
public class AssociateController {

    private AssociateServiceImpl service;

    @GetMapping
    ResponseEntity<Page<Associate>> findAll(@RequestParam(required = false, defaultValue = "0") Integer page) {
        return new ResponseEntity<>(service.findAll(page), HttpStatus.OK);
    }
    @PostMapping
    ResponseEntity<Associate> save(@Valid @RequestPart Associate associate, @RequestPart MultipartFile profile, @RequestPart MultipartFile banner) {
        return new ResponseEntity<>(service.saveWithImages(associate, profile, banner), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    ResponseEntity<Associate> findById(@PathVariable String id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    ResponseEntity<Associate> updateById(@PathVariable String id, @RequestPart Associate associate, @RequestPart MultipartFile profile, @RequestPart MultipartFile banner) {
        return new ResponseEntity<>(service.updateWithImages(id, associate, profile, banner), HttpStatus.CREATED);
    }

}
