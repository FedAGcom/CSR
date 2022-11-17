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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/all")
    @ApiOperation(value = "Список всех пользователей.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список пользователей получен.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public Page<UserResponse> getAllUsers(@PageableDefault(size = 5) Pageable pageable) {
        return userService.getAllUsers(pageable);
    }

    @GetMapping
    @ApiOperation(value = "Получение пользователя по token",
            notes = "Предоставьте token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь получен",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
//    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok().body(userService.getUserAndBalanceAndAllActiveItemsAndFavoritePackAndBestItem(token));
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

    @PatchMapping("/insert-trade-url")
    @ApiOperation(value = "Добавление trade url по steam id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trade url внесана в базу",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public void insertTradeUrl(@RequestHeader("Authorization") String confirmationToken, @RequestHeader("TradeURL") String tradeUrl) {
        userService.insertTradeUrl(confirmationToken, tradeUrl);
    }

    @GetMapping("/count")
    @ApiOperation(value = "Получение количества пользователей.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Количество получено",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public Long totalUserCount() {
        return userService.totalUsersCount();
    }
}
