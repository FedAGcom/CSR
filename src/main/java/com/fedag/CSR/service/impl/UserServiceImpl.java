package com.fedag.CSR.service.impl;

import com.fedag.CSR.dto.request.UserRequest;
import com.fedag.CSR.dto.response.UserResponse;
import com.fedag.CSR.dto.update.UserUpdate;
import com.fedag.CSR.enums.ItemsWonStatus;
import com.fedag.CSR.exception.EntityNotFoundException;
import com.fedag.CSR.mapper.mapperImpl.UserMapper;
import com.fedag.CSR.model.Balance;
import com.fedag.CSR.model.ItemsWon;
import com.fedag.CSR.model.User;
import com.fedag.CSR.repository.BalanceRepository;
import com.fedag.CSR.repository.ItemsWonRepository;
import com.fedag.CSR.repository.UserRepository;
import com.fedag.CSR.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final BalanceRepository balanceRepository;
    private final ItemsWonRepository itemsWonRepository;
    private final PasswordEncoder encoder;

    public Page<UserResponse> getAllUsers(Pageable pageable) {
        log.info("Получение всех пользователей");
        Page<UserResponse> userResponses = userMapper.modelToDto(userRepository.findAll(pageable));
        log.info("Все пользователи получены");
        return userResponses;
    }

    @Override
    public Map<String, Object> getUserAndBalanceAndAllActiveItemsAndFavoritePackAndBestItem(String token) {
        log.info("Получение пользователя, баланса, выигранных предметов на балансе, самых дорогих выигранных предметов и " +
                "наиболее часто открываемых кейсов");
        Map<String, Object> getUserDetails = new LinkedHashMap<>();

        User user = userRepository.findUserByConfirmationToken(token)
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));

        getUserDetails.put("nickNameSteam", user.getUserName());
        getUserDetails.put("role", user.getRole());
        getUserDetails.put("steamAvatarMedium", user.getSteamAvatarMediumLink());
        getUserDetails.put("tradeUrl", user.getTradeUrl());
        getUserDetails.put("balance", balanceRepository.findAllByUserId(user.getId())
                .stream()
                .map(Balance::getCoins)
                .findFirst());
        getUserDetails.put("bestItemIdAndPrice",
                itemsWonRepository.findAllByUsersId(user.getId())
                        .stream()
                        .distinct()
                        .collect(Collectors.toMap
                                (items -> items.getItems().getItemId(), ItemsWon::getItemPrice,
                                        (duplicate1, duplicate2) -> {
                                            return duplicate1;
                                        }))
                        .entrySet()
                        .stream()
                        .max(Map.Entry.comparingByValue()));
        getUserDetails.put("favoritePackIdAndCount",
                itemsWonRepository.findAllByUsersId(user.getId())
                        .stream()
                        .map(pack -> pack.getPacks().getId())
                        .collect(Collectors.toMap(Function.identity(), value -> 1, Integer::sum))
                        .entrySet()
                        .stream()
                        .max(Map.Entry.comparingByValue()));
        getUserDetails.put("itemsIdActiveAll",
                itemsWonRepository.findAllByUsersIdAndItemsWonStatus(user.getId(), ItemsWonStatus.ON_BALANCE)
                        .stream()
                        .map(itemsWon -> itemsWon.getItems().getItemId()));
        log.info("Пользователь, баланс, выигранные предметы на балансе, самые дорогие выигранные предметы и " +
                "наиболее часто открываемые кейсы получены");
        return getUserDetails;
    }

    @Override
    public UserResponse getUser(String token) {
        log.info("Получение пользователя c token");
        UserResponse userResponse = null;
        Optional<User> optional = userRepository.findUserByConfirmationToken(token);
        if (optional.isPresent()) {
            User user = optional.get();
            userResponse = userMapper.modelToDto(user);
            log.info("Получен пользователь");
            return userResponse;
        } else throw new EntityNotFoundException("Пользователь не найден");

    }

    @Override
    public void save(UserRequest user) {
        log.info("Создание пользователя");
        user.setPassword(encoder.encode(user.getPassword()));
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

    @Override
    @Transactional
    public void insertTradeUrl(String confirmationToken, String tradeURL) {
        log.info("Обновление трейд ссылки");
        Optional<User> optional = userRepository.findUserByConfirmationToken(confirmationToken);
        if (optional.isPresent()) {
            User source = optional.get();
            source.setTradeUrl(tradeURL);
            userRepository.save(source);
            log.info("Трейд ссылка пользователя обновлена");
        } else throw new EntityNotFoundException("Пользователь не найден");
    }
}


