package com.fedag.CSR.mapper;

import com.fedag.CSR.dto.request.ItemRequest;
import com.fedag.CSR.dto.response.ItemResponse;
import com.fedag.CSR.dto.update.ItemUpdate;
import com.fedag.CSR.model.Item;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ItemMapper {
    ItemResponse modelToDto(Item item);

    List<ItemResponse> modelToDto(List<Item> item);

    Page<ItemResponse> modelToDto(Page<Item> itemPage);

    public Item dtoToModel(ItemRequest dto);

    public Item dtoToModel(ItemUpdate dto);

    public Item dtoToModel(ItemResponse dto);

}
