package com.fedag.CSR.service.impl;

import com.fedag.CSR.model.Balance;
import com.fedag.CSR.model.Item;
import com.fedag.CSR.model.ItemsWon;
import com.fedag.CSR.repository.BalanceRepository;
import com.fedag.CSR.repository.ItemRepository;
import com.fedag.CSR.repository.ItemsWonRepository;
import com.fedag.CSR.repository.UserRepository;
import com.fedag.CSR.service.ItemsWonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemsWonServiceImpl implements ItemsWonService {
    private final ItemsWonRepository itemsWonRepository;

    @Override
    public void add(ItemsWon itemWon) {
        log.info("Создание выйгрывшего предмета");
        itemsWonRepository.save(itemWon);
        log.info("Выйгранный предмет записан в items_won.");
    }

    @Override
    public ItemsWon findById(Long id) {
        return null;
    }
}
