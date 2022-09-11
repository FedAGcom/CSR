package com.fedag.CSR.service.impl;

import com.fedag.CSR.exception.EntityNotFoundException;
import com.fedag.CSR.model.Pack;
import com.fedag.CSR.model.User;
import com.fedag.CSR.repository.PackRepository;
import com.fedag.CSR.repository.UserRepository;
import com.fedag.CSR.service.SteamService;
import com.fedag.CSR.service.utils.SteamRoulette;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
@AllArgsConstructor
public class SteamServiceImpl implements SteamService {

    PackRepository packRepository;

    UserRepository userRepository;

    @Override
    public void getItemFromCase(BigDecimal packId, BigDecimal userId) {
        Pack pack = packRepository.findById(packId).orElseThrow(
                () -> new EntityNotFoundException("Case с id: " + packId + "не найден"));
        User user = userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("User с id: " + userId + "не найден"));
        SteamRoulette.generateResult(user, pack);
    }
}


