package com.fedag.CSR.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BalanceRequest {
    private BigDecimal id;
    private int coins;
    private List<BigDecimal> itemId;
}
