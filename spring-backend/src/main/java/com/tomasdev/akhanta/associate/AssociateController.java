package com.tomasdev.akhanta.associate;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/associate")
@AllArgsConstructor
public class AssociateController {

    private AssociateService associateService;

    @GetMapping
    public ResponseEntity<Page<Associate>> findAllAssociates(@RequestParam(required = false, defaultValue = "0") Integer page) {
        return ResponseEntity.ok().body(associateService.findAllAssociates(page));
    }

    @GetMapping("/{id}")
    ResponseEntity<Associate> findAssociateById(@PathVariable String id) {
        return ResponseEntity.ok().body(associateService.findAssociateById(id));
    }

}
