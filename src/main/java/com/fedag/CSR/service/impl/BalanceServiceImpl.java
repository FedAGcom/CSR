package com.fedag.CSR.service.impl;

import com.fedag.CSR.enums.ItemsWonStatus;
import com.fedag.CSR.exception.EntityNotFoundException;
import com.fedag.CSR.model.Balance;
import com.fedag.CSR.mapper.BalanceMapper;
import com.fedag.CSR.model.ItemsWon;
import com.fedag.CSR.repository.BalanceRepository;
import com.fedag.CSR.repository.ItemsWonRepository;
import com.fedag.CSR.service.BalanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * class BalanceServiceImpl for create connections between BalanceRepository and BalanceController.
 * Implementation of {@link BalanceService} interface.
 * Wrapper for {@link BalanceRepository} and plus business logic.
 *
 * @author Kirill Soklakov
 * @since 2022-09-01
 * @version 1.1
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BalanceServiceImpl implements BalanceService {

    private final BalanceRepository balanceRepository;
    private final BalanceMapper balanceMapper;
    private final ItemsWonRepository itemsWonRepository;

    @Override
    public Balance findById(BigDecimal id) {
        log.info("Получение баланса c Id: {}", id);
        Balance result = balanceRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Баланс с Id: {} не найден", id);
                    throw new EntityNotFoundException("Balance", "Id", id);
                });
        log.info("Баланс c Id: {} получен", id);
        return result;
    }

    @Override
    public Page<Balance> findAll(Pageable pageable) {
        log.info("Получение страницы с балансами");
        Page<Balance> result = balanceRepository.findAll(pageable);
        log.info("Страница с балансами получена");
        return result;
    }

    @Override
    @Transactional
    public Balance create(Balance balance) {
        log.info("Создание баланса");
        Balance result = balanceRepository.save(balance);
        log.info("Баланс создан");
        return result;
    }

    @Override
    @Transactional
    public Balance update(BigDecimal id, Balance balance) {
        log.info("Обновление баланса с Id: {}", id);
        Balance target = this.findById(id);
        Balance update = balanceMapper.merge(balance, target);
        Balance result = balanceRepository.save(update);
        log.info("Баланс с Id: {} обновлен", id);
        return result;
    }

    @Override
    @Transactional
    public void deleteById(BigDecimal id) {
        log.info("Удаление баланса с Id: {}", id);
        this.findById(id);
        balanceRepository.deleteById(id);
        log.info("Баланс с Id: {} удален", id);
    }

    @Override
    public Double soldAllItemsByBalanceId(BigDecimal id) {
        Balance result = balanceRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Баланс с Id: {} не найден", id);
                    throw new EntityNotFoundException("Balance", "Id", id);
                });
        Double profit = 0.0;
        List<ItemsWon> listItems = new ArrayList<>();
        for (ItemsWon i: result.getItemsWon()){
            if (i.getItemsWonStatus() == ItemsWonStatus.ON_BALANCE){
                i.setItemsWonStatus(ItemsWonStatus.SOLD);
                i.setItemWonSailedTime(LocalDateTime.now());
                listItems.add(i);
                profit = profit + i.getItem_price();
                itemsWonRepository.save(i);
            }
        }
        result.setCoins(result.getCoins() + profit);
        result.setItemsWon(listItems);

        balanceRepository.save(result);

        return profit;
    }

}