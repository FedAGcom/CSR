package com.fedag.CSR.services.impl;

import com.fedag.CSR.models.User;
import com.fedag.CSR.repositories.UserRepository;
import com.fedag.CSR.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    @Override
    public User getUser(int id) {
        User user = null;

        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            user = optional.get();
        }

        return user;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deteleUser(int id) {
        userRepository.deleteById(id);
    }
}
