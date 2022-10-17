package com.fedag.CSR.controller;


import com.fedag.CSR.dto.request.ItemRequest;
import com.fedag.CSR.dto.response.ItemResponse;
import com.fedag.CSR.dto.update.ItemUpdate;
import com.fedag.CSR.model.Item;
import com.fedag.CSR.service.impl.ItemServiceImpl;
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

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemServiceImpl itemService;

    @GetMapping("/{id}")
    @ApiOperation(value = "Получение предмета по id.",
            notes = "Предоставьте id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Предмет получен",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public Item getItem(@PathVariable BigDecimal id){
        return itemService.getItem(id);
    }

    @GetMapping
    @ApiOperation(value = "Список всех предметов.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список предметов получен.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public Page<ItemResponse> getAllItems(@PageableDefault(size = 5) Pageable pageable) {
        Page<ItemResponse> page = itemService.getAllItems(pageable);
        System.out.println(page.getTotalElements());
        return itemService.getAllItems(pageable);
    }

    @PutMapping
    @ApiOperation(value = "Обновление предмета.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Предмет обновлен.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PreAuthorize("hasAuthority('admin')")
    public void updateItem(@RequestBody ItemUpdate item) {
        itemService.updateItem(item);
    }

    @PostMapping
    @ApiOperation(value = "Создание нового предмета.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Предмет создан.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PreAuthorize("hasAuthority('admin')")
    public void createUser(@RequestBody Item item) {
        itemService.create(item);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Удаление предмета",
            notes = "Предоставьте id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Предмет удален.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PreAuthorize("hasAuthority('admin')")
    public void delete(@PathVariable BigDecimal id) {
        itemService.deleteItemById(id);
    }
}
