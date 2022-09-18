package com.fedag.CSR.controller;

import com.fedag.CSR.model.Pack;
import com.fedag.CSR.model.WinChance;
import com.fedag.CSR.repository.PackRepository;
import com.fedag.CSR.repository.WinChanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class WinChanceController {
    private final PackRepository packRepository;
    private final WinChanceRepository winChanceRepository;

    @GetMapping("/spin/{id}")
    public Long spinCase(@PathVariable Long id) {
        List<WinChance> listWinChance = winChanceRepository.getWinChanceByPackId(id);

        double total = 0;

        for (int i = 0; i < listWinChance.size(); i++) {
            double modifiedWinChance = listWinChance.get(i).getWinChance() * 10_000;
            listWinChance.get(i).setWinChance(modifiedWinChance);
            total = total + modifiedWinChance;
        }
        Random random = new Random();
        double value = random.nextInt((int) total);

        double counterLeft = 0;
        double counterRight = listWinChance.get(0).getWinChance();

        for (int i = 0; i < listWinChance.size(); i++) {
            if (value >= counterLeft && value <= counterRight) {
                return listWinChance.get(i).getItemId();
            }
            counterLeft = counterRight;
            counterRight = counterRight + listWinChance.get(i).getWinChance();
        }
        return null;
    }
}