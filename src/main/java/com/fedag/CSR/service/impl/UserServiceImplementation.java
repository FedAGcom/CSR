package com.fedag.CSR.service.impl;

import com.fedag.CSR.dto.request.UserRequest;
import com.fedag.CSR.dto.update.UserUpdate;
import com.fedag.CSR.mapper.UserMapper;
import com.fedag.CSR.model.User;
import com.fedag.CSR.repository.UserRepository;
import com.fedag.CSR.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

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
    public void save(UserRequest user) {
        userRepository.save(userMapper.dtoToModel(user));
    }

    @Override
    public void deteleUser(int id) {
        userRepository.deleteById(id);
    }

    public void update(UserUpdate user) {
        User updatedUser = userMapper.dtoToUpdatedModel(user);
        userRepository.save(updatedUser);
    }
}
