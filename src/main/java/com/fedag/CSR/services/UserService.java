package com.fedag.CSR.services;

import com.fedag.CSR.models.User;

import java.util.List;

public interface UserService {

    public List<User> getAllUsers();

    public void saveUser(User user);

    public User getUser(int id);

    public void deteleUser(int id);
}
