package com.fedag.CSR.service.impl;

import com.fedag.CSR.dto.request.UserRequest;
import com.fedag.CSR.dto.response.UserResponse;
import com.fedag.CSR.dto.update.UserUpdate;
import com.fedag.CSR.mapper.mapperImpl.UserMapper;
import com.fedag.CSR.model.User;
import com.fedag.CSR.repository.UserRepository;
import com.fedag.CSR.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public Page<UserResponse> getAllUsers(Pageable pageable) {
        log.info("Получение всех пользователей");
        Page<UserResponse> userResponses = userMapper.modelToDto(userRepository.findAll(pageable));
        log.info("Все пользователи получены");
        return userResponses;
    }

    @Override
    public UserResponse getUser(BigDecimal id) {
        log.info("Получение пользователя c id {}", id);
        UserResponse userResponse = null;
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            User user = optional.get();
            userResponse = userMapper.modelToDto(user);
        }
        log.info("Получен пользователь с id {}", id);
        return userResponse;

    }

    @Override
    public void save(UserRequest user) {
        log.info("Создание пользователя");
        userRepository.save(userMapper.dtoToModel(user));
        log.info("Пользователь создан");
    }

    @Override
    public void deteleUser(BigDecimal id) {
        log.info("Удаление пользователя c id {}", id);
        userRepository.deleteById(id);
        log.info("Пользователь c id {} удален", id);
    }

    public void update(UserUpdate user) {
        log.info("Обновление пользователя с id {}", user.getId());
        userRepository.save(userMapper.dtoToModel(user));
        log.info("Пользователь с id {} обновлен", user.getId());
    }
}
