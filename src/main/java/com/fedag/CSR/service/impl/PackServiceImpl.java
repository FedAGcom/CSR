package com.fedag.CSR.service.impl;

import com.fedag.CSR.dto.response.PackResponse;
import com.fedag.CSR.enums.PackStatus;
import com.fedag.CSR.mapper.PackMapper;
import com.fedag.CSR.model.Item;
import com.fedag.CSR.model.Pack;
import com.fedag.CSR.model.WinChance;
import com.fedag.CSR.repository.ItemRepository;
import com.fedag.CSR.repository.PackRepository;
import com.fedag.CSR.repository.WinChanceRepository;
import com.fedag.CSR.service.ItemService;
import com.fedag.CSR.service.PackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PackServiceImpl implements PackService {
    private final PackRepository packRepository;
    private final ItemService itemService;
    private final WinChanceRepository winChanceRepository;
    private final PackMapper packMapper;
    private final RestTemplate restTemplate;

    @Override
    @Transactional
    public Pack create(String pack, MultipartFile file) throws IOException {
        log.info("Создание кейса");

        JSONObject jsonObject = new JSONObject(pack);

        Pack newPack = createImage(file, new Pack());

        Double price = jsonObject.getDouble("price");
        newPack.setPrice(price);
        newPack.setTitle((String) jsonObject.get("title"));
        newPack.setStatus(PackStatus.USED);
        JSONArray itemsArray = jsonObject.getJSONArray("items");
        packRepository.save(newPack);

        for (int i = 0; i < itemsArray.length(); i++) {

            Item item = new Item();
            WinChance winChance = new WinChance();
            JSONObject arrayJson = itemsArray.getJSONObject(i);

            item.setPack(newPack);
            item.setType(arrayJson.getString("type"));
            item.setTitle(arrayJson.getString("title"));
            item.setRare(arrayJson.getString("rare"));
            item.setQuality(arrayJson.getString("quality"));

            winChance.setItem(item);
            winChance.setPack(newPack);
            winChance.setWinChance(arrayJson.getDouble("winchance"));

            String medianPrice = getItemPriceFromSteam(item);
            item.setPrice(Double.valueOf(medianPrice.substring(0, medianPrice.length() - 5).replace(",", ".")));
            //Формирование картинки предмета
            String itemIcon = getItemIcon(item);
            item.setIconItemId(itemIcon);
            itemService.create(item);
            winChanceRepository.saveAndFlush(winChance);
        }
        return newPack;
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
    public PackResponse findById(BigDecimal id) {
        log.info("Получение кейса c Id: {}", id);
        PackResponse packResponse = null;
        Optional<Pack> optional = packRepository.findById(id);
        if (optional.isPresent()) {
            Pack pack = optional.get();
            packResponse = packMapper.toResponse(pack);
        }
        return packResponse;
    }

    @Override
    public Page<PackResponse> findAll(Pageable pageable) {
        log.info("Получение страницы с кейсами");
        Page<PackResponse> result = packMapper.modelToDto(packRepository.findAll(pageable));
        log.info("Страница с кейсами получена");
        return result;
    }

    @Override
    public Pack findPackById(BigDecimal id) {
        Optional<Pack> optional = packRepository.findById(id);
        Pack result = null;
        if (optional.isPresent()) {
            result = optional.get();
        }
        return result;
    }

    @Override
    @Transactional
    public Pack updatePack(String pack, MultipartFile multipartFile) throws IOException {
        log.info("Создание кейса");

        JSONObject jsonObject = new JSONObject(pack);

        BigDecimal id = jsonObject.getBigDecimal("id");

        Optional<Pack> oldPack = packRepository.findById(id);
        if (oldPack.isPresent()) {
            Pack newPack = oldPack.get();
            newPack.setStatus(PackStatus.OUT_DATE);
        }
        return create(pack, multipartFile);
    }

    public Pack createImage(MultipartFile file, Pack result) throws IOException {
        result.setImage(Base64.toBase64String(file.getBytes()));
        result.setImageType(file.getContentType());
        return result;
    }

    public String getItemIcon(Item item) {
        String itemName = item.getTitle() + " (" + item.getQuality() + ")";
        ResponseEntity<String> response = restTemplate
                .getForEntity("https://steamcommunity.com/market/listings/730/"
                        + itemName + "/render?start=0&count=1&currency=3&language=english&format=json", String.class);
        JSONObject jsonAssets = new JSONObject(response.getBody());
        JSONObject jsonObjectAssets = (JSONObject) jsonAssets.get("assets");
        String[] iconUrlArray = String.valueOf(jsonObjectAssets).split("\"icon_url\":\"");

        int counter = 0;
        StringBuilder sb = new StringBuilder();
        while (iconUrlArray[1].charAt(counter) != 34) {
            sb.append(iconUrlArray[1].charAt(counter));
            counter++;
        }

        String iconUrlFromApi = iconUrlArray[1].substring(0, sb.length());
        String finalIconUrl = "http://cdn.steamcommunity.com/economy/image/" + iconUrlFromApi;
        return finalIconUrl;
    }

    public String getItemPriceFromSteam(Item item) throws IOException {
        String hashName = item.getTitle() + " (" + item.getQuality() + ")";

        String basedUrl = "https://steamcommunity.com/market/priceoverview/?appid=730&currency=5&market_hash_name="
                + hashName;
        String[] urlArray = basedUrl.split(" ");
        String newUrl = String.join("%20", urlArray);
        newUrl.substring(0, newUrl.length() - 3);


        URL url = new URL(newUrl);
        String json = IOUtils.toString(url, StandardCharsets.UTF_8);
        JSONObject jsonObjectForPrice = new JSONObject(json);
        String medianPrice = (String) jsonObjectForPrice.get("median_price");
        return medianPrice;
    }
}