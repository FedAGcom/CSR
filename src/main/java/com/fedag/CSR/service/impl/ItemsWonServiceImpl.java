package com.fedag.CSR.service.impl;

import com.fedag.CSR.enums.ItemsWonStatus;
import com.fedag.CSR.model.*;
import com.fedag.CSR.repository.*;
import com.fedag.CSR.service.ItemsWonService;
import liquibase.pro.packaged.V;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemsWonServiceImpl implements ItemsWonService {
    private final ItemsWonRepository itemsWonRepository;

    private final BalanceRepository balanceRepository;

    private final UserRepository userRepository;
    private final WinChanceRepository winChanceRepository;


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
            profit += itemsWon.getItemPrice();
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
        balance.setCoins(balance.getCoins() + itemsWon.getItemPrice());

        itemsWonRepository.save(itemsWon);
        balanceRepository.save(balance);
    }
    @Override
    public List<Map<String, Object>> getLastWiningItems(){
        List<ItemsWon> lastItems = itemsWonRepository.getLastItemsWonLimit10();
        return createResultList(lastItems);
    }

    @Override
    public List<Map<String, Object>> getTheBestWiningItems(){
        List<ItemsWon> lastItems = itemsWonRepository.getTheBestItemsWonLimit10();
        return createResultList(lastItems);
    }

    @Override
    public long totalPacksOpened() {
        return itemsWonRepository.count();
    }

    public List<Map<String, Object>> createResultList(List<ItemsWon> lastItems){
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (ItemsWon lastItem : lastItems) {
            Map<String, Object> itemMap = new LinkedHashMap<>();
            itemMap.put("icon", lastItem.getItems().getIconItemId());
            itemMap.put("rare", lastItem.getItems().getRare());
            itemMap.put("type", lastItem.getItems().getType());
            itemMap.put("item_title", lastItem.getItems().getTitle());
            itemMap.put("pack_title", lastItem.getPacks().getTitle());
            itemMap.put("user_name", lastItem.getUsers().getUserName());
            itemMap.put("user_icon", lastItem.getUsers().getSteamFullAvatarLink());
            resultList.add(itemMap);
        }
        return resultList;
    }
}
