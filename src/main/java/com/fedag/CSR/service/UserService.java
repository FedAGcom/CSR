package com.fedag.CSR.service;

import com.fedag.CSR.dto.request.UserRequest;
import com.fedag.CSR.dto.response.UserResponse;
import com.fedag.CSR.dto.update.UserUpdate;
import com.fedag.CSR.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService<T> {

//    List<User> getAllUsers();
    Page<T> getAllUsers(Pageable pageable);
    UserResponse getUser(int id);

    void save(UserRequest user);

    void deteleUser(int id);

    void update(UserUpdate user);
}
