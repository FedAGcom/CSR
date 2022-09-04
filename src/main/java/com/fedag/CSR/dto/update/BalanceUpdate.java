package com.fedag.CSR.dto.update;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BalanceUpdate {
    private BigDecimal id;
    private BigDecimal userId;
    private int coins;
    private List<BigDecimal> balanceItemsId;
}
