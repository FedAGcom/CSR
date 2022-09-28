package com.fedag.CSR.controller;

import com.fedag.CSR.service.impl.WinChanceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class WinChanceController{

    private final WinChanceServiceImpl winChanceService;

    @GetMapping("/spin/{id}")
    public Long spinPack(@PathVariable Long id, @RequestHeader("Authorization") String userToken) {
        return winChanceService.spinCase(id, userToken);
    }

}