package com.fedag.CSR.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@CrossOrigin(origins = "http://5.101.51.15/", maxAge = 3600)
@Controller
@RequestMapping("/shop-verification-ZqmaWxg7l9")
public class PayPalychController {

    @GetMapping
    public ResponseEntity<String> getCode() {
        return ResponseEntity.ok().body("shop-verification-ZqmaWxg7l9");
    }
}