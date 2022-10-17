package com.fedag.CSR.controller;

import com.fedag.CSR.service.impl.WinChanceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
@CrossOrigin(origins = "http://5.101.51.15/", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class WinChanceController{

    private final WinChanceServiceImpl winChanceService;

    @GetMapping("/spin/{id}")
    public BigDecimal spinPack(@PathVariable BigDecimal id, @RequestHeader("Authorization") String userToken) {
        return winChanceService.spinCase(id, userToken);
    }

}