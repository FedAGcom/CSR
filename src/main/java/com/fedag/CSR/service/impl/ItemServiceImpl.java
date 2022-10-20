package com.fedag.CSR.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedag.CSR.dto.response.ItemResponse;
import com.fedag.CSR.dto.update.ItemUpdate;
import com.fedag.CSR.exception.EntityNotFoundException;
import com.fedag.CSR.mapper.ItemMapper;
import com.fedag.CSR.model.Item;
import com.fedag.CSR.repository.Impl.ItemRepositoryImpl;
import com.fedag.CSR.repository.ItemRepository;
import com.fedag.CSR.repository.UserRepository;
import com.fedag.CSR.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;

    private String steamURL = "https://steamcommunity.com";

    private String getPriceOfItemSteamUrl = "https://steamcommunity.com/market/priceoverview/";
    private final ObjectMapper mapper;
    private final ItemRepositoryImpl itemRepositoryImpl;

    private final ItemRepository itemRepository;

    private final ItemMapper itemMapper;

    @Override
    public Page<ItemResponse> getAllItems(Pageable pageable) {
        log.info("Получение всех предметов");
        Page<ItemResponse> result = itemMapper.modelToDto(itemRepository.findAll(pageable));
        log.info("Все предметы получены");
        return result;
    }

    @Override
    public ItemResponse findById(BigDecimal id) {
        log.info("Получение предмета c Id: {}", id);
        ItemResponse itemResponse = null;
        Optional<Item> optional = itemRepository.findById(id);
        if (optional.isPresent()) {
            Item item = optional.get();
            itemResponse = itemMapper.modelToDto(item);
        }
        log.info("Предмет получен с id {}", id);
        return itemResponse;
    }

    @Override
    public Item create(Item item) {
        log.info("Создание предмета");
        Item result = itemRepository.save(item);
        log.info("Предмет создан");
        return result;
    }

    @Override
    public Item updateItem(ItemUpdate item) {
        log.info("Обновление предмета");
        Item result = itemRepository.save(itemMapper.dtoToModel(item));
        log.info("Предмет обновлён");
        return result;
    }

    @Override
    public void deleteItemById(BigDecimal id) {
        log.info("Удаление предмета с id {}", id);
        itemRepository.deleteById(id);
        log.info("Предмет удалёнс id {}", id);
    }

    @Override
    public Item getItem(BigDecimal id) {
        //ItemResponse itemResponse = null;
        Item result = null;
        Optional<com.fedag.CSR.model.Item> optional = itemRepository.findById(id);
        if (optional.isPresent()) {
            result = optional.get();
//            itemResponse = itemMapper.modelToDto(item);
        }
        log.info("Предмет получен с id {}", id);
        return result;
    }

    @Override
    public void addAllItems(BigDecimal id) {
        log.info("Добавление вещей пользователя с id {}", id);
        String steam_id = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Пользователь не найден")
        ).getSteamId();
        ResponseEntity<String> response = restTemplate.getForEntity(
                steamURL + "/profiles/" + steam_id + "/inventory/json/730/2"
                , String.class);

        try {
            JsonNode rootNode = mapper.readValue(response.getBody(), JsonNode.class);
            List<Item> items = jsonParser(rootNode);
            itemRepositoryImpl.addAllUserItems(items);
        } catch (JsonProcessingException e) {
            log.error("Ошибка парсинга Json в методе addAllItems(): {}", e.getMessage());
            throw new RuntimeException("Ошибка парсинга Json в методе addAllItems()" + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        log.info("Вещи пользователя с id {} добавлены", id);
    }

    private List<Item> jsonParser(JsonNode rootNode) throws UnsupportedEncodingException, JsonProcessingException {
        List<Item> result = new ArrayList<>();
        JsonNode childNode = rootNode.get("rgDescriptions");
        Iterator<JsonNode> iterator = childNode.elements();

        while (iterator.hasNext()) {
            JsonNode childNode3 = iterator.next();
            Item item = new Item();

            //добавление картинки предмета


            // формируем имя предмета
            String name = childNode3.get("name").toString();
            item.setTitle(name.substring(1, name.length() - 1));
            // формирование цены предмета
            String primer = "https://steamcommunity.com/market/priceoverview/?appid=730&currency=3&market_hash_name=M4A1-S | Hyper Beast (Minimal Wear)";


            // определяем тип вещи
            String[] qualities = childNode3.get("market_name").toString().split(" ");
            String quality = qualities[qualities.length - 1];
            if (quality.contains("(") && quality.contains(")")) {
                quality = qualities[qualities.length - 1]
                        .substring(1, qualities[qualities.length - 1].length() - 2);
                item.setQuality(quality);
            } else if (!quality.contains("(") && quality.contains(")")) {
                String intermittentResult = qualities[qualities.length - 2] + " " + qualities[qualities.length - 1];
                quality = intermittentResult.substring(1, intermittentResult.length() - 2);
                item.setQuality(quality);
            }
            // определяем rare и тип вещи
            String[] rareAndType = childNode3.get("type").toString().split(" ");
            StringBuilder rare = new StringBuilder();
            for (int i = 0; i < rareAndType.length - 1; i++) {
                rare.append(rareAndType[i] + " ");
            }
            item.setRare(rare.substring(1, rare.length() - 1));
            item.setType(rareAndType[rareAndType.length - 1]
                    .substring(0, rareAndType[rareAndType.length - 1].length() - 1));
            // назначаем цену
            if (!item.getType().equals("Collectible") && !item.getType().equals("Graffiti") && item.getQuality() != null) {
                RestTemplate restTemplate1 = new RestTemplate();
                String s = item.getTitle() + " (" + item.getQuality() + ")";
                System.out.println(s);
                ResponseEntity<String> response = restTemplate1.getForEntity(
                        "https://steamcommunity.com/market/priceoverview/?appid=730&currency=5&market_hash_name=" + s
                        , String.class);
                JsonNode rootNodeForPrice = mapper.readValue(response.getBody(), JsonNode.class);
                JsonNode childNodeForPrice = rootNodeForPrice.get("median_price");
                String price = childNodeForPrice.toString()
                        .substring(1, childNodeForPrice.toString().length() - 6)
                        .replace(",", ".");
                item.setPrice(Double.parseDouble(price));
            }
            result.add(item);
        }
        return result;
    }
}


