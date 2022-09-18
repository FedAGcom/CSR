package com.fedag.CSR.mapper.mapperImpl;


import com.fedag.CSR.dto.request.PackRequest;
import com.fedag.CSR.dto.response.PackResponse;
import com.fedag.CSR.dto.update.PackUpdate;
import com.fedag.CSR.mapper.PackMapper;
import com.fedag.CSR.model.Item;
import com.fedag.CSR.model.Pack;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


@Component
@RequiredArgsConstructor
public class PackMapperImpl implements PackMapper {

    private final ModelMapper mapper;

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Pack.class, PackResponse.class)
                .addMappings(m -> m.skip(PackResponse::setPackItemsId))
                .setPostConverter(toDtoConverter());
    }

    private Converter<Pack, PackResponse> toDtoConverter() {
        return context -> {
            Pack source = context.getSource();
            PackResponse destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private void mapSpecificFields(Pack source, PackResponse destination) {
        List<BigDecimal> listInt = new ArrayList<>();
//        for (Item i: source.getItems()){
//            listInt.add(i.getItemId());
//        }
//        destination.setPackItemsId(listInt);
    }
    @Override
    public Page<PackResponse> modelToDto(Page<Pack> packPage) {
        return packPage
                .map(new Function<Pack, PackResponse>() {
                    @Override
                    public PackResponse apply(Pack entity) {
                        return modelToDto(entity);
                    }
                });
    }
    @Override
    public PackResponse modelToDto(Pack pack) {
        return mapper.map(pack, PackResponse.class);
    }
    @Override
    public Pack dtoToModel(PackRequest pack) {
        return mapper.map(pack, Pack.class);
    }

    @Override
    public Pack dtoToModel(PackUpdate pack) {
        return mapper.map(pack, Pack.class);
    }
}
