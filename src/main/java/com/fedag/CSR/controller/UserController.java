package com.fedag.CSR.controller;

import com.fedag.CSR.dto.request.UserRequest;
import com.fedag.CSR.dto.update.UserUpdate;
import com.fedag.CSR.mapper.UserMapper;
import com.fedag.CSR.model.User;
import com.fedag.CSR.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;

    @GetMapping("/")
    @ApiOperation(value = "Shows the list of all users.", response = User.class)
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Shows the specified user.",
            notes = "Please provide an id.",
            response = User.class)
    public User getUser(@PathVariable int id) {
        return userService.getUser(id);
    }

    @PostMapping("/")
    @ApiOperation(value = "Creates a new user.")
    public void createUser(@RequestBody UserRequest user) {
        userService.save(user);
    }

    @PutMapping("/")
    @ApiOperation(value = "Updates the specified user.")
    public void updateUser(@RequestBody UserUpdate user) {
        userService.update(user);
    }


    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deletes the specified user.",
            notes = "Please provide an id.")
    public String delete(@PathVariable int id) {
        userService.deteleUser(id);
        return "User with ID = " + id + " was deleted.";
    }

}
