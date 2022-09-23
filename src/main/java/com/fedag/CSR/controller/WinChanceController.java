package com.fedag.CSR.controller;

import com.fedag.CSR.service.impl.WinChanceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class WinChanceController {

    private final WinChanceServiceImpl winChanceService;

    @GetMapping("/spin/{id}")
    public Long spinPack(@PathVariable Long id) {
        return winChanceService.spinCase(id);
    }


}