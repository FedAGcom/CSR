package com.fedag.CSR.controller;

import com.fedag.CSR.service.impl.SkinifyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SkinifyController {
    private final SkinifyServiceImpl skinifyService;


    @PostMapping("/createDeposit")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<?> createDepositPost(@RequestHeader("Authorization") String userToken) {
        String url = skinifyService.createDeposit(userToken);
        System.out.println(url);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(url)).build();
    }
}