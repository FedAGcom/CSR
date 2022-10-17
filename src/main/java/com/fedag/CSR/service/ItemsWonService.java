package com.fedag.CSR.service;

import com.fedag.CSR.model.ItemsWon;

import java.math.BigDecimal;

public interface ItemsWonService {
    void add(ItemsWon itemWon);

    void sellAllItemsByBalanceId(BigDecimal id);

    void sellAnItemWonByUserIdAndItemId(BigDecimal itemId, String userToken);
}
