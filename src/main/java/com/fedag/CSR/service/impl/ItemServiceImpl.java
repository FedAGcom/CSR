package com.fedag.CSR.service.impl;

import com.fedag.CSR.dto.request.ItemRequest;
import com.fedag.CSR.dto.response.ItemResponse;
import com.fedag.CSR.dto.update.ItemUpdate;
import com.fedag.CSR.mapper.ItemMapper;
import com.fedag.CSR.model.Item;
import com.fedag.CSR.repository.ItemRepository;
import com.fedag.CSR.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
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
}

