package com.fedag.CSR.controller;

import com.fedag.CSR.dto.request.UserRequest;
import com.fedag.CSR.dto.response.UserResponse;
import com.fedag.CSR.dto.update.UserUpdate;
import com.fedag.CSR.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping
    @ApiOperation(value = "Список всех пользователей.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список пользователей получен.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PreAuthorize("hasAuthority('user')")
    public Page<UserResponse> getAllUsers(@PageableDefault(size = 5) Pageable pageable) {
        return userService.getAllUsers(pageable);
    }
    @GetMapping("/{id}")
    @ApiOperation(value = "Получение пользователя по id.",
            notes = "Предоставьте id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь получен",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
                    })
    @PreAuthorize("hasAuthority('user')")
    public UserResponse getUser(@PathVariable BigDecimal id) {
        return userService.getUser(id);
    }
    @PostMapping
    @ApiOperation(value = "Создание нового пользователя.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь создан.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PreAuthorize("hasAuthority('admin')")
    public void createUser(@RequestBody UserRequest user) {
        userService.save(user);
    }
    @PutMapping
    @ApiOperation(value = "Обновление пользователя.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь обновлен.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PreAuthorize("hasAuthority('admin')")
    public void updateUser(@RequestBody UserUpdate user) {
        userService.update(user);
    }
    @DeleteMapping("/{id}")
    @ApiOperation(value = "Удаление пользователя",
            notes = "Предоставьте id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь удален.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PreAuthorize("hasAuthority('admin')")
    public String delete(@PathVariable BigDecimal id) {
        userService.deteleUser(id);
        return "User with ID = " + id + " was deleted.";
    }

}
