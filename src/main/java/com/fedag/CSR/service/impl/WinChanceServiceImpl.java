package com.fedag.CSR.service.impl;

import com.fedag.CSR.model.WinChance;
import com.fedag.CSR.repository.WinChanceRepository;
import com.fedag.CSR.service.WinChanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class WinChanceServiceImpl implements WinChanceService {

    private final WinChanceRepository winChanceRepository;
    @Override
    public Long spinCase(Long id) {
        List<WinChance> listWinChance = winChanceRepository.getWinChanceByPackId(id);
        int total = 0;




        for (int i = 0; i < listWinChance.size(); i++) {
            double modifiedWinChance = listWinChance.get(i).getWinChance() * 10_000;
            listWinChance.get(i).setWinChance(modifiedWinChance);
            total = total + (int) modifiedWinChance;
        }
        Random random = new Random();
        double value = random.nextInt(total);

        double counterLeft = 0;
        double counterRight = listWinChance.get(0).getWinChance();

        for (int i = 0; i < listWinChance.size(); i++) {
            if (value >= counterLeft && value <= counterRight) {
                return listWinChance.get(i).getItemId();
            }
            counterLeft = counterRight;
            counterRight = counterRight + listWinChance.get(i).getWinChance();
        }
        return listWinChance.get(listWinChance.size() - 1).getItemId();
    }
}
