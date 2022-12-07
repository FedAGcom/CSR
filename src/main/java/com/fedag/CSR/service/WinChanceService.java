package com.fedag.CSR.service;

import java.math.BigDecimal;

public interface WinChanceService {
    BigDecimal spinCase(BigDecimal id, String userToken);

}