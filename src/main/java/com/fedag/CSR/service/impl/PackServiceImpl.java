package com.fedag.CSR.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedag.CSR.exception.EntityNotFoundException;
import com.fedag.CSR.model.Item;
import com.fedag.CSR.model.Pack;
import com.fedag.CSR.repository.ItemRepository;
import com.fedag.CSR.repository.PackRepository;
import com.fedag.CSR.service.PackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PackServiceImpl implements PackService {
    private final PackRepository packRepository;
    private final ItemRepository itemRepository;

    @Override
    public Page<Pack> findAll(Pageable pageable) {
        log.info("Получение страницы с кейсами");
        Page<Pack> result = packRepository.findAll(pageable);
        log.info("Страница с кейсами получена");
        return result;
    }

    @Override
    @Transactional
    public Pack create(Pack pack) throws IOException {
        log.info("Создание кейса");
        Pack result = packRepository.save(pack);
        List<Item> items = pack.getItems().stream().toList();

        for (Item item : items) {
            item.setPack(pack);
            String hashName = item.getTitle() + " (" + item.getQuality() + ")";

            String basedUrl = "https://steamcommunity.com/market/priceoverview/?appid=730&currency=5&market_hash_name="
                    + hashName;
            String[] urlArray = basedUrl.split(" ");
            String newUrl = String.join("%20", urlArray);
            newUrl.substring(0, newUrl.length() - 3);

            URL url = new URL(newUrl);
            String json = IOUtils.toString(url, StandardCharsets.UTF_8);
            JSONObject jsonObject = new JSONObject(json);
            String medianPrice = (String) jsonObject.get("median_price");

            item.setPrice(Double.valueOf(medianPrice.substring(0, medianPrice.length() - 5).replace(",", ".")));
            itemRepository.save(item);

        }
        log.info("Кейс создан");
        return result;
    }


    @Override
    public void deletePackById(BigDecimal id) {
        log.info("Удаление кейса.");
        packRepository.deleteById(id);
        log.info("Удаление кейса завершено.");
    }

    @Override
    @Transactional
    public void deleteById(BigDecimal id) {
        log.info("Удаление кейса с Id: {}", id);
        this.findById(id);

        packRepository.deleteById(id);
        log.info("Кейс с Id: {} удален", id);
    }

    @Override
    public Pack findById(BigDecimal id) {
        log.info("Получение кейса c Id: {}", id);
        Pack result = packRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Кейс с Id: {} не найден", id);
                    throw new EntityNotFoundException("Case", "Id", id);
                });
        log.info("Кейс c Id: {} получен", id);
        return result;
    }

}