package com.fedag.CSR.service.impl;

import com.fedag.CSR.exception.EntityNotFoundException;
import com.fedag.CSR.model.Balance;
import com.fedag.CSR.mapper.BalanceMapper;
import com.fedag.CSR.model.Item;
import com.fedag.CSR.repository.BalanceRepository;
import com.fedag.CSR.service.BalanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
        final List<Item> balanceItems = balance.getItems();
        balance.setItems(balanceItems);
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

}
