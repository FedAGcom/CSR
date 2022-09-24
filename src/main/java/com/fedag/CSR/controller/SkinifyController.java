package com.fedag.CSR.controller;

import com.fedag.CSR.enums.DepositStatus;
import com.fedag.CSR.service.impl.SkinifyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
        System.out.println(url); // удалить!
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(url)).build();
    }

    @GetMapping("/check-status/{depositId}")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<DepositStatus> checkDepositStatus(@PathVariable Long depositId,
                                                            @RequestHeader("Authorization") String userToken) {


        return ResponseEntity.ok().body(skinifyService.checkDepositStatus(depositId, userToken));
    }
}