package com.fedag.CSR.service;

import com.fedag.CSR.dto.request.ItemRequest;
import com.fedag.CSR.dto.response.ItemResponse;
import com.fedag.CSR.dto.update.ItemUpdate;
import com.fedag.CSR.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface ItemService {
    Page<ItemResponse> getAllItems(Pageable pageable);
    Item create(Item item);
    Item updateItem(ItemUpdate item);
    void deleteItemById(BigDecimal id);
    Item getItem(BigDecimal id);

    void addAllItems(BigDecimal id);
}
