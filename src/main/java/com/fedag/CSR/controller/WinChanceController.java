package com.fedag.CSR.controller;

import com.fedag.CSR.repository.PackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class WinChanceController {

    private final PackRepository packRepository;

    @GetMapping("/zalypa")
    public ResponseEntity<?> showFuckingWinChance() {
        return ResponseEntity.ok().body(packRepository.findByTitle("title1").get().getWinChances());
    }
}
