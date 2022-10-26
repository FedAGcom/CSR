package com.fedag.CSR.mapper.mapperImpl;

import com.fedag.CSR.dto.request.ItemRequest;
import com.fedag.CSR.dto.response.ItemResponse;
import com.fedag.CSR.dto.update.ItemUpdate;
import com.fedag.CSR.mapper.ItemMapper;
import com.fedag.CSR.model.Item;
import com.fedag.CSR.model.WinChance;
import com.fedag.CSR.repository.PackItemRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ItemMapperImpl implements ItemMapper {

    private final ModelMapper mapper;
    private final PackItemRepository itemRepository;

    @Override
    public ItemResponse modelToDto(Item item) {
        return new ItemResponse()
                .setId(item.getItemId())
                .setType(item.getType())
                .setTitle(item.getTitle())
                .setRare(item.getRare())
                .setQuality(item.getQuality())
                .setPrice(item.getPrice())
                .setIconItemId(item.getIconItemId())
                .setWinChance(item.getWinChances()
                        .stream()
                        .map(WinChance::getWinChance)
                        .findFirst().orElse((double) 0));

    }

    @Override
    public List<ItemResponse> modelToDto(List<Item> item) {
        return item
                .stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ItemResponse> modelToDto(Page<Item> itemPage) {
        return itemPage
                .map(new Function<Item, ItemResponse>() {
                    @Override
                    public ItemResponse apply(Item entity) {
                        return modelToDto(entity);
                    }
                });
    }

    @Override
    public Item dtoToModel(ItemRequest dto) {
        return mapper.map(dto, Item.class);
    }

    @Override
    public Item dtoToModel(ItemUpdate dto) {
        return mapper.map(dto, Item.class);
    }

    @Override
    public Item dtoToModel(ItemResponse dto) {
        return mapper.map(dto, Item.class);
    }


}
