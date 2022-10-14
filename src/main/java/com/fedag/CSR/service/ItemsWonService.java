package com.fedag.CSR.service;

import com.fedag.CSR.model.ItemsWon;

public interface ItemsWonService {
    void add(ItemsWon itemWon);

    ItemsWon findById(Long id);

}
