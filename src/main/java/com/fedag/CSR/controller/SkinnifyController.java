package com.fedag.CSR.controller;

import com.fedag.CSR.service.impl.SkinnyfyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class SkinnifyController {
    private final SkinnyfyServiceImpl skinnyService;

    @Autowired
    public SkinnifyController(SkinnyfyServiceImpl skinnyService) {
        this.skinnyService = skinnyService;
    }

    @PostMapping("/createDeposit")
    @PreAuthorize("hasAuthority('admin')")
    public void createDepositPost() {
        skinnyService.createDeposit();
    }
}
