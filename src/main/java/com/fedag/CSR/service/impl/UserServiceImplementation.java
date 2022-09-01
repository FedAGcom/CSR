package com.fedag.CSR.service.impl;

import com.fedag.CSR.dto.request.UserRequest;
import com.fedag.CSR.dto.response.UserResponse;
import com.fedag.CSR.dto.update.UserUpdate;
import com.fedag.CSR.mapper.UserMapper;
import com.fedag.CSR.model.User;
import com.fedag.CSR.repository.UserRepository;
import com.fedag.CSR.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

//    @Override
//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userMapper.modelToDto(userRepository.findAll(pageable));
    }

    @Override
    public UserResponse getUser(int id) {
        UserResponse userResponse = null;
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            User user = optional.get();
            userResponse = userMapper.modelToDto(user);
        }
        return userResponse;
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
        userRepository.save(userMapper.dtoToModel(user));
    }
}
