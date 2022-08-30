package com.example.CSR.services;

import com.example.CSR.entities.User;

import java.util.List;

public interface UserService {

    public List<User> getAllUsers();

    public void saveUser(User user);

    public User getUser(int id);

    public void deteleUser(int id);
}
