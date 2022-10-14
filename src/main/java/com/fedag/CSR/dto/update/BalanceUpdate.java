package com.fedag.CSR.dto.update;

import com.fedag.CSR.model.ItemsWon;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BalanceUpdate {
    private BigDecimal id;
    private BigDecimal userId;
    private Double coins;
    private List<ItemsWon> balanceItemWon;
}
