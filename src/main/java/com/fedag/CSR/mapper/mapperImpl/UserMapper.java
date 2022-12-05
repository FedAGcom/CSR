package com.fedag.CSR.mapper.mapperImpl;

import com.fedag.CSR.dto.request.UserRequest;
import com.fedag.CSR.dto.response.UserResponse;
import com.fedag.CSR.dto.update.UserUpdate;
import com.fedag.CSR.model.Balance;
import com.fedag.CSR.model.User;
import com.fedag.CSR.repository.BalanceRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper mapper;
    private final BalanceRepository balanceRepository;

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(UserRequest.class, User.class)
                .addMappings(m -> m.skip(User::setCreated))
                .setPostConverter(toDtoConverterFromRequest());

        mapper.createTypeMap(UserUpdate.class, User.class)
                .addMappings(m -> m.skip(User::setCreated))
                .setPostConverter(toDtoConverterFromUpdate());
    }

    private Converter<UserRequest, User> toDtoConverterFromRequest() {
        return context -> {
            UserRequest source = context.getSource();
            User destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private Converter<UserUpdate, User> toDtoConverterFromUpdate() {
        return context -> {
            UserUpdate source = context.getSource();
            User destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private void mapSpecificFields(UserRequest source, User destination) {
        destination.setCreated(LocalDateTime.now());
    }

    private void mapSpecificFields(UserUpdate source, User destination) {
        destination.setCreated(source.getCreated());
    }

    public Page<UserResponse> modelToDto(Page<User> userPage) {
        return userPage
                .map(new Function<User, UserResponse>() {
                    @Override
                    public UserResponse apply(User entity) {
                        return modelToDto(entity);
                    }
                });
    }

    public UserResponse modelToDto(User user) {
        return new UserResponse()
                .setId(user.getId())
                .setUserName(user.getUserName())
                .setRole(user.getRole())
                .setSteamAvatarMedium(user.getSteamAvatarMediumLink())
                .setBalance(balanceRepository.findAllByUserId(user.getId())
                        .stream()
                        .map(Balance::getCoins)
                        .findFirst().orElse(Double.valueOf("0.00")))
                .setTradeUrl(user.getTradeUrl())
                .setEmail(user.getEmail().equals("fedag@gmail.com") ? null : user.getEmail());
    }

    public User dtoToModel(UserRequest user) {
        return mapper.map(user, User.class);
    }

    public User dtoToModel(UserUpdate user) {
        return mapper.map(user, User.class);
    }
}
