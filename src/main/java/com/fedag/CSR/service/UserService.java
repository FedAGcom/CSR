package com.fedag.CSR.service;

import com.fedag.CSR.dto.request.UserRequest;
import com.fedag.CSR.dto.response.UserResponse;
import com.fedag.CSR.dto.update.UserUpdate;
import com.fedag.CSR.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Map;

public interface UserService<T> {
    Page<T> getAllUsers(Pageable pageable);

    UserResponse getUser(String token);

    void save(UserRequest user);

    void deteleUser(BigDecimal id);

    void update(UserUpdate user);

    Map<String, Object> getUserAndBalanceAndAllActiveItemsAndFavoritePackAndBestItem(String token);

    void insertTradeUrl(String confirmationToken, String tradeURL);
}
