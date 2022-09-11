package com.fedag.CSR.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

public interface SteamService {
    void getItemFromCase(BigDecimal packId, BigDecimal userId);
}
