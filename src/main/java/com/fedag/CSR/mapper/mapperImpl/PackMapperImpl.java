package com.fedag.CSR.mapper.mapperImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fedag.CSR.dto.request.PackRequest;
import com.fedag.CSR.dto.response.PackResponse;
import com.fedag.CSR.dto.update.PackUpdate;
import com.fedag.CSR.mapper.PackMapper;
import com.fedag.CSR.model.Item;
import com.fedag.CSR.model.Pack;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class PackMapperImpl implements PackMapper {

    private final ObjectMapper objectMapper;

    @Override
    public PackResponse toResponse(Pack pack) {
        return new PackResponse()
                .setId(pack.getId())
                .setTitle(pack.getTitle())
                .setPrice(pack.getPrice())
                .setPackItemsId(pack.getItems()
                        .stream()
                        .map(Item::getItemId)
                        .collect(Collectors.toList()));
    }


    public Pack fromRequest(PackRequest packRequest) {
        return objectMapper.convertValue(packRequest, Pack.class);
    }

    public Pack fromRequestUpdate(PackUpdate packUpdate) {
        return objectMapper.convertValue(packUpdate, Pack.class);
    }

}