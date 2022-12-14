package com.fedag.CSR.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BalanceResponse {

    private BigDecimal id;
    private int coins;
    private BigDecimal userId;
    private List<BigDecimal> balanceItemsId;
}
