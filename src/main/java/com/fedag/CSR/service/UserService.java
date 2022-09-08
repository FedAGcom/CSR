package com.fedag.CSR.service;

import com.fedag.CSR.dto.request.UserRequest;
import com.fedag.CSR.dto.response.UserResponse;
import com.fedag.CSR.dto.update.UserUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

public abstract class UserService<T> {
    public abstract Page<T> getAllUsers(Pageable pageable);
    public abstract UserResponse getUser(BigDecimal id);

    public abstract void save(UserRequest user);

    public abstract void deteleUser(BigDecimal id);

    public abstract void update(UserUpdate user);
}
