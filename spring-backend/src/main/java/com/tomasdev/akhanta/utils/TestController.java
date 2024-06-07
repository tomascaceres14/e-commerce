package com.tomasdev.akhanta.utils;


import com.tomasdev.akhanta.auth.ChangePasswordDTO;
import com.tomasdev.akhanta.security.jwt.TokenBlacklist;
import com.tomasdev.akhanta.security.jwt.TokenBlacklistRepository;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class TestController {
    private final TokenBlacklistRepository repository;

    @GetMapping
    public ResponseEntity<?> changePassword(@RequestParam String test) {
        repository.save(new TokenBlacklist(null, test));
        System.out.println(repository.findByToken(test));
        System.out.println(repository.existsByToken(test));
        return ResponseEntity.status(HttpStatus.SC_RESET_CONTENT).build();
    }
}
