package com.fedag.CSR.service;

import com.fedag.CSR.dto.request.UserRequest;
import com.fedag.CSR.dto.update.UserUpdate;
import com.fedag.CSR.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUser(int id);

    void save(UserRequest user);

    void deteleUser(int id);

    void update(UserUpdate user);
}
