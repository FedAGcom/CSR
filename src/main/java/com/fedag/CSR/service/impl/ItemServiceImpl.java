package com.fedag.CSR.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedag.CSR.dto.request.ItemRequest;
import com.fedag.CSR.dto.response.ItemResponse;
import com.fedag.CSR.dto.update.ItemUpdate;
import com.fedag.CSR.exception.EntityNotFoundException;
import com.fedag.CSR.mapper.ItemMapper;
import com.fedag.CSR.model.Item;
import com.fedag.CSR.repository.ItemRepository;
import com.fedag.CSR.repository.UserRepository;
import com.fedag.CSR.repository.impl.ItemRepositoryImpl;
import com.fedag.CSR.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    private final RestTemplate restTemplate;
    @Value("${steam.url}")
    private String steamURL;
    private final ObjectMapper mapper;

    private final UserRepository userRepository;

    private final ItemRepositoryImpl repository;

    @Override
    public Page<ItemResponse> getAllItems(Pageable pageable) {
        log.info("Получение всех предметов");
        Page<ItemResponse> result = itemMapper.modelToDto(itemRepository.findAll(pageable));
        log.info("Все предметы получены");
        return result;
    }

    @Override
    public Item create(ItemRequest item) {
        log.info("Создание предмета");
        Item result = itemRepository.save(itemMapper.dtoToModel(item));
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
        log.info("Удаление предмета с id{}: ",id);
        itemRepository.deleteById(id);
        log.info("Предмет удалёнс id{}:" ,id);
    }

    @Override
    public ItemResponse getItem(BigDecimal id) {
        log.info("Получение одного предмета с id{} : ", id);
        ItemResponse itemResponse = null;
        Optional<Item> optional = itemRepository.findById(id);
        if (optional.isPresent()) {
            Item item = optional.get();
            itemResponse = itemMapper.modelToDto(item);
        }
        log.info("Предмет получен с id{} :", id);
        return itemResponse;
    }

    @Override
    public void addAllItems(BigDecimal id) {
        log.info("Добавление вещей пользователя с id {}", id);
        String steam_id = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Пользователь не найден")
        ).getSteam_id();
        ResponseEntity<String> response;
        if (steam_id.matches(".*[A-Za-z]+.*")) {
            response = restTemplate.getForEntity(
                    steamURL + "/id/" + steam_id + "/inventory/json/730/2", String.class);
        } else {
            response = restTemplate.getForEntity(
                    steamURL + "/profiles/" + steam_id + "/inventory/json/730/2"
                    , String.class);
        }

        try {
            JsonNode rootNode = mapper.readValue(response.getBody(), JsonNode.class);
            List<Item> items = jsonParser(rootNode);
            repository.addAllUserItems(items);
        } catch (JsonProcessingException e) {
            log.error("Ошибка парсинга Json в методе addAllItems(): {}", e.getMessage());
            throw new RuntimeException("Ошибка парсинга Json в методе addAllItems()" + e.getMessage());
        }
        log.info("Вещи пользователя с id {} добавлены", id);
    }

    private List<Item> jsonParser(JsonNode rootNode) {
        List<Item> result = new ArrayList<>();
        JsonNode childNode = rootNode.get("rgDescriptions");
        Iterator<JsonNode> iterator = childNode.elements();

        while (iterator.hasNext()) {
            JsonNode childNode3 = iterator.next();
            Item item = new Item();

            // формируем steam_id предмета
            String classid = childNode3.get("classid").toString();
            String instanceid = childNode3.get("instanceid").toString();
            item.setSteam_id(classid.substring(1, classid.length() - 1)
                    + "_" + instanceid.substring(1, instanceid.length() - 1));

            // формируем имя предмета
            String name = childNode3.get("name").toString();
            item.setTitle(name.substring(1, name.length() - 1));

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

            result.add(item);
        }
        return result;
    }
}

