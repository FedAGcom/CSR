package com.fedag.CSR.controllers;

import com.fedag.CSR.models.User;
import com.fedag.CSR.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getAllUsers() {

        List<User> allUsers = userService.getAllUsers();

        return allUsers;
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable int id) {
        User user = userService.getUser(id);

        return user;
    }

    @PostMapping("/users")
    public User create(@RequestBody User user) {
        userService.saveUser(user);

        return user;
    }

    @PutMapping("/users")
    public User update(@RequestBody User user) {
        userService.saveUser(user);

        return user;
    }


    @DeleteMapping("/users/{id}")
    public String delete(@PathVariable int id) {

        userService.deteleUser(id);

        return "User with ID = " + id + " was deleted.";
    }

}
