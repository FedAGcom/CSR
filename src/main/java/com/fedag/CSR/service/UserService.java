package com.fedag.CSR.service;

import com.fedag.CSR.dto.request.UserRequest;
import com.fedag.CSR.dto.response.UserResponse;
import com.fedag.CSR.dto.update.UserUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface UserService<T> {
    Page<T> getAllUsers(Pageable pageable);
    UserResponse getUser(BigDecimal id);

    void save(UserRequest user);

    void deteleUser(BigDecimal id);

    void update(UserUpdate user);
}
