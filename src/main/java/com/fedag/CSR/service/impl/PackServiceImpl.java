package com.fedag.CSR.service.impl;

import com.fedag.CSR.dto.response.PackResponse;
import com.fedag.CSR.enums.PackStatus;
import com.fedag.CSR.exception.HttpClientErrorExceptionCustom;
import com.fedag.CSR.exception.ObjectNotFoundException;
import com.fedag.CSR.mapper.PackMapper;
import com.fedag.CSR.model.Item;
import com.fedag.CSR.model.Pack;
import com.fedag.CSR.model.WinChance;
import com.fedag.CSR.repository.PackRepository;
import com.fedag.CSR.repository.WinChanceRepository;
import com.fedag.CSR.service.ItemService;
import com.fedag.CSR.service.PackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PackServiceImpl implements PackService {
    private final PackRepository packRepository;
    private final ItemService itemService;
    private final WinChanceRepository winChanceRepository;
    private final PackMapper packMapper;

    @Override
    @Transactional
    public Pack create(String pack) throws IOException {
        log.info("Создание кейса");

        JSONObject jsonObject = new JSONObject(pack);

//        Pack newPack = createImage(file, new Pack());
        Pack newPack = new Pack();

        newPack.setPrice(jsonObject.getDouble("price"));
        newPack.setTitle((String) jsonObject.get("title"));
        newPack.setStatus(PackStatus.USED);
        newPack.setImage((String) jsonObject.get("file"));
        packRepository.save(newPack);
        log.info("Кейс создан");
        return newPack;
    }

    @Override
    public void addItemsToPack(BigDecimal packId, String item) {
        Optional<Pack> pack = packRepository.findById(packId);
        WinChance winChance = new WinChance();
        Item requestItem = new Item();
        JSONObject jsonObject = new JSONObject(item);

        requestItem.setPrice(jsonObject.getDouble("price"));
        requestItem.setType(jsonObject.getString("type"));
        requestItem.setTitle(jsonObject.getString("title"));
        requestItem.setRare(jsonObject.getString("rare"));
        requestItem.setQuality(jsonObject.getString("quality"));
        String itemIcon = getItemIcon(requestItem);
        requestItem.setIconItemId(itemIcon);

        winChance.setItem(requestItem);
        winChance.setPack(pack.get());
        winChance.setWinChance(jsonObject.getDouble("winchance"));

        itemService.create(requestItem);
        winChanceRepository.saveAndFlush(winChance);
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
    public Page<PackResponse> findAll(Pageable pageable) {
        log.info("Получение страницы с кейсами");
        Page<PackResponse> result = packMapper.modelToDto(packRepository.findAll(pageable));
        List<PackResponse> listPacks = result.getContent()
                .stream()
                .filter(pack -> pack.getStatus() == PackStatus.USED)
                .collect(Collectors.toList());
        Page<PackResponse> page = new PageImpl<>(listPacks);
        log.info("Страница с кейсами получена");
        return page;
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
    public PackResponse findById(BigDecimal id) {
        log.info("Получение кейса c Id: {}", id);
        PackResponse packResponse = null;
        Optional<Pack> optional = packRepository.findById(id);
        if (optional.isPresent() && optional.get().getStatus() == PackStatus.USED) {
            Pack pack = optional.get();
            packResponse = packMapper.toResponse(pack);
        }
        return packResponse;
    }

    @Override
    @Transactional()
    public Map<String, Object> updatePack(String pack) throws IOException {
        log.info("Обновление кейса");
        Map<String, Object> responseMap = new HashMap<>();

        JSONObject jsonObject = new JSONObject(pack);

        BigDecimal id = jsonObject.getBigDecimal("id");

        Optional<Pack> oldPack = packRepository.findById(id);
        if (oldPack.isPresent()) {
            Pack newPack = oldPack.get();
            if (newPack.getStatus() == PackStatus.USED) {
                newPack.setStatus(PackStatus.OUT_DATE);

                // создание картинки из БД по изменяемому кейсу
//                if (multipartFile.isEmpty()) {
//
//                    MultipartFile oldMultipartFile
//                            = new MockMultipartFile("newFile",
//                            null,
//                            newPack.getImageType(),
//                            Base64.decode(newPack.getImage()));
//                    create(pack, oldMultipartFile);
//
//                } else
                create(pack);

                responseMap.put("error", false);
                responseMap.put("message", "Pack updated Successfully");
            } else {
                responseMap.put("error", true);
                responseMap.put("message", "Something went wrong");
            }

        }
        return responseMap;
    }


// Кодировка картинки в БД (если потребуется)
//    public Pack createImage(MultipartFile file, Pack result) throws IOException {
//        result.setImage(Base64.toBase64String(file.getBytes()));
//        result.setImageType(file.getContentType());
//        return result;
//    }

    public String getItemIcon(Item item) {
        StringBuilder itemName = new StringBuilder();
        try {
            if (item.getType().equalsIgnoreCase("Knife")) {
                itemName.append("%E2%98%85%20")
                        .append(item.getTitle().replace("|", "%7C").replace(" ", "%20"))
                        .append("%20%28")
                        .append(item.getQuality())
                        .append("%29");
            } else itemName
                    .append(item.getTitle().replace("|", "%7C").replace(" ", "%20"))
                    .append("%20%28")
                    .append(item.getQuality())
                    .append("%29");

            String url = "https://steamcommunity.com/market/listings/730/" + itemName;
            log.info("Получение иконки предмета " + url);

            Document document = null;
            try {
                document = Jsoup.connect(url).get();
            } catch (Exception e) {
                throw new HttpClientErrorExceptionCustom("Not found page with items");
            }
            log.info("Иконка предмета получена");
            return document.select("#mainContents > div.market_page_fullwidth.market_listing_firstsection > div > div.market_listing_largeimage > img")
                    .attr("src");
        } catch (
                HttpClientErrorExceptionCustom TooManyRequests) {
            log.warn("Too Many Requests to steam community market, getting an icon from the wiki");
            itemName.setLength(0);
            itemName.append(item.getTitle().replace(" | ", "/").replace(" ", "-").toLowerCase());
            String url = "https://wiki.cs.money/ru/weapons/" + itemName;
            log.info("Получение иконки предмета " + url);
            Document document = null;
            try {
                document = Jsoup.connect(url).get();
            } catch (IOException httpStatusException) {
                throw new ObjectNotFoundException("Item doesn't exist on wiki market");
            }
            log.info("Иконка предмета получена");
            return document.select("#skins > div.xleatfemcgmqbklagaxecqnufs > div.ugedsptljnltawgotsbdhtgruj > link")
                    .attr("href");
        }
    }

    public String getItemPriceFromSteam(Item item) throws IOException {

        String itemName;
        if (item.getType().equalsIgnoreCase("Knife")) {
            itemName = ("%E2%98%85%20")
                    + (item.getTitle().replace("|", "%7C").replace(" ", "%20"))
                    + ("%20%28")
                    + (item.getQuality().replace(" ", "%20"))
                    + ("%29");
        } else itemName =
                (item.getTitle().replace("|", "%7C").replace(" ", "%20"))
                        + ("%20%28")
                        + (item.getQuality().replace(" ", "%20"))
                        + ("%29");

        String url = "https://steamcommunity.com/market/priceoverview/?appid=730&currency=5&market_hash_name="
                + itemName;
        log.info("Получение цены предмента " + url);

        String json = IOUtils.toString(URI.create(url), StandardCharsets.UTF_8);
        JSONObject jsonObjectForPrice = new JSONObject(json);
        String price;
        try {
            price = (String) jsonObjectForPrice.get("median_price");
        } catch (JSONException e) {
            try {
                price = (String) jsonObjectForPrice.get("lowest_price");
            } catch (JSONException ex) {
                throw new ObjectNotFoundException("Item doesn't exist on steam community market");
            }
        }
        log.info("Цена получена");
        return price;
    }
}