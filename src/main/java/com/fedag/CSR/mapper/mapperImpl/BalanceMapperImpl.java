package com.fedag.CSR.mapper.mapperImpl;

import com.fedag.CSR.dto.request.BalanceRequest;
import com.fedag.CSR.dto.update.BalanceUpdate;
import com.fedag.CSR.dto.response.BalanceResponse;
import com.fedag.CSR.mapper.BalanceMapper;
import com.fedag.CSR.model.Balance;
import com.fedag.CSR.model.Item;
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
import java.util.stream.Collectors;

/**
 * class BalanceMapperImpl is implementation of {@link BalanceMapper} interface.
 *
 * @author Kirill Soklakov
 * @since 2022-08-31
 */
@Component
@RequiredArgsConstructor
public class BalanceMapperImpl implements BalanceMapper {
    private final ModelMapper mapper;

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(Balance.class, BalanceResponse.class)
                .addMappings(m -> m.skip(BalanceResponse::setBalanceItemsId))
                .setPostConverter(toDtoConverter());
    }

    private Converter<Balance, BalanceResponse> toDtoConverter() {
        return context -> {
            Balance source = context.getSource();
            BalanceResponse destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }


    private void mapSpecificFields(Balance source, BalanceResponse destination) {
        List<BigDecimal> listInt = new ArrayList<>();
//        for (Item i: source.getItems()){
//            listInt.add(i.getItemId());
//        }
        destination.setBalanceItemsId(listInt);
    }
    public BalanceResponse modelToDto(Balance balance) {
        return mapper.map(balance, BalanceResponse.class);
    }

    public List<BalanceResponse> modelToDto(List<Balance> balance) {
        return balance
                .stream()
                .map(this::modelToDto)
                .collect(Collectors.toList());
    }

    public Page<BalanceResponse> modelToDto(Page<Balance> balancePage) {
        return balancePage
                .map(new Function<Balance, BalanceResponse>() {
                    @Override
                    public BalanceResponse apply(Balance entity) {
                        return modelToDto(entity);
                    }
                });
    }

    public Balance dtoToModel(BalanceRequest dto) {
        return mapper.map(dto, Balance.class);
    }

    public Balance dtoToModel(BalanceUpdate dto) {
        return mapper.map(dto, Balance.class);
    }

    public Balance dtoToModel(BalanceResponse dto) {
        return mapper.map(dto, Balance.class);
    }

    public List<Balance> dtoToModel(List<BalanceResponse> dto) {
        return dto
                .stream()
                .map(this::dtoToModel)
                .collect(Collectors.toList());
    }

    @Override
    public Balance merge(Balance source, Balance target) {
        if (source.getCoins() != 0) {
            target.setCoins(source.getCoins());
        }
        return target;
    }
}