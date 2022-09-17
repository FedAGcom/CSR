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
    public ResponseEntity<?> spinCase(@PathVariable Long id) {
        List<WinChance> listWinChance = winChanceRepository.getWinChanceByPackId(id);
        ResponseEntity<?> result = null;
        double total = 0;
        double counterLeft = 0;
        double counterRight = 0;

        for (int i = 0; i < listWinChance.size(); i++) {
            double modifiedWinChance = listWinChance.get(i).getWinChance() * 10_000;
            listWinChance.get(i).setWinChance(modifiedWinChance);
            total = total + modifiedWinChance;
        }
        Random random = new Random();
        double value = random.nextInt((int) total);

        if (value <= listWinChance.get(0).getWinChance()) {
            result = ResponseEntity.ok().body(listWinChance.get(0).getItemId());
        }

        counterRight = listWinChance.get(0).getWinChance();
        counterLeft = listWinChance.get(0).getWinChance();

        try {
            for (int i = 1; i < listWinChance.size(); i++) {
                if (value >= counterLeft && value <= (listWinChance.get(i + 1).getWinChance() + counterRight)) {
                    result = ResponseEntity.ok().body(listWinChance.get(i).getItemId());
                }
                counterRight = counterRight + listWinChance.get(i).getWinChance();
                counterLeft = counterLeft + listWinChance.get(i).getWinChance();
            }
        } catch (IndexOutOfBoundsException e) {
            result = ResponseEntity.ok().body(listWinChance.get(listWinChance.size() - 1).getItemId());
        }

        return result;
    }

//    @GetMapping("/{pack_id}")
//    public ResponseEntity<?> spin(@PathVariable BigDecimal id) {
//        Pack pack = packRepository.findById(id).get();
//        return null;
//    }

}
