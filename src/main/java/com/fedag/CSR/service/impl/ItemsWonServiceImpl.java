package com.fedag.CSR.service.impl;

import com.fedag.CSR.enums.ItemsWonStatus;
import com.fedag.CSR.model.*;
import com.fedag.CSR.repository.*;
import com.fedag.CSR.service.ItemsWonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemsWonServiceImpl implements ItemsWonService {
    private final ItemsWonRepository itemsWonRepository;

    private final BalanceRepository balanceRepository;

    private final UserRepository userRepository;


    @Override
    public void add(ItemsWon itemWon) {
        log.info("Создание выйгрывшего предмета");
        itemsWonRepository.save(itemWon);
        log.info("Выйгранный предмет записан в items_won.");
    }

    @Override
    public void sellAllItemsByBalanceIdAndItemsWonStatus(BigDecimal id) {
        List<ItemsWon> itemsWonList = itemsWonRepository.findAllByBalancesIdAndItemsWonStatus(id, ItemsWonStatus.ON_BALANCE);
        Double profit = 0.0;
        for (ItemsWon itemsWon : itemsWonList) {
            profit += itemsWon.getItem_price();
            itemsWon.setItemsWonStatus(ItemsWonStatus.SOLD);
            itemsWon.setItemWonSailedTime(LocalDateTime.now());
            itemsWonRepository.save(itemsWon);
        }
        Optional<Balance> balance = balanceRepository.findById(id);
        Double currentAmountOfCoins = balance.get().getCoins();
        balance.get().setCoins(currentAmountOfCoins + profit);
        balanceRepository.save(balance.get());
    }

    @Override
    public void sellAnItemWonByUserIdAndItemId(BigDecimal itemId, String userToken){
        Optional<User> user = userRepository.findUserByConfirmationToken(userToken);
        BigDecimal userId = user.get().getId();
        ItemsWon itemsWon = itemsWonRepository.findTopItemsWonByUsersIdAndItemsItemIdAndItemsWonStatus(userId, itemId, ItemsWonStatus.ON_BALANCE);
        itemsWon.setItemsWonStatus(ItemsWonStatus.SOLD);
        itemsWon.setItemWonSailedTime(LocalDateTime.now());
        Balance balance = itemsWon.getBalances();
        balance.setCoins(balance.getCoins() + itemsWon.getItem_price());

        itemsWonRepository.save(itemsWon);
        balanceRepository.save(balance);
    }
}
