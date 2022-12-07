package com.fedag.CSR.service;

import com.fedag.CSR.model.ItemsWon;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ItemsWonService {
    void add(ItemsWon itemWon);

    void sellAllItemsByBalanceIdAndItemsWonStatus(BigDecimal id);

    void sellAnItemWonByUserIdAndItemId(BigDecimal itemId, String userToken);
    List<Map<String, Object>> getLastWiningItems();
    List<Map<String, Object>> getTheBestWiningItems();

    long totalPacksOpened();
}
