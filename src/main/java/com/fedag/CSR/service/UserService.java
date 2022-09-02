package com.fedag.CSR.service;

import com.fedag.CSR.dto.request.UserRequest;
import com.fedag.CSR.dto.response.UserResponse;
import com.fedag.CSR.dto.update.UserUpdate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService<T> {

    Page<T> getAllUsers(Pageable pageable);
    UserResponse getUser(int id);

    void save(UserRequest user);

    void deteleUser(int id);

    void update(UserUpdate user);
}
