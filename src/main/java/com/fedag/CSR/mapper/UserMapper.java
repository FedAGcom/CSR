package com.fedag.CSR.mapper;

import com.fedag.CSR.dto.request.UserRequest;
import com.fedag.CSR.dto.update.UserUpdate;
import com.fedag.CSR.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper mapper;

    @PostConstruct
    public void setupMapper() {
        mapper.createTypeMap(UserRequest.class, User.class)
                .addMappings(m -> m.skip(User::setCreated))
                .setPostConverter(toDtoConverter());
    }

    private Converter<UserRequest, User> toDtoConverter() {
        return context -> {
            UserRequest source = context.getSource();
            User destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private void mapSpecificFields(UserRequest source, User destination) {
        destination.setCreated(LocalDateTime.now());
    }

    public User dtoToModel(UserRequest user) {
        return mapper.map(user, User.class);
    }

    public User dtoToUpdatedModel(UserUpdate user) {
        return mapper.map(user, User.class);
    }
}
