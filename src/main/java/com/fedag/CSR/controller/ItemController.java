package com.fedag.CSR.controller;


import com.fedag.CSR.dto.request.ItemRequest;
import com.fedag.CSR.dto.response.ItemResponse;
import com.fedag.CSR.dto.update.ItemUpdate;
import com.fedag.CSR.service.impl.ItemServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemServiceImpl itemService;

    @GetMapping("/{id}")
    @ApiOperation(value = "Выводит предмет, находящийся в базе данных")
    public ItemResponse getItem(@PathVariable BigDecimal id){
        return itemService.getItem(id);
    }

    @GetMapping
    @ApiOperation(value = "Выводит все предметы, находящиеся в базе данных")
    public Page<ItemResponse> getAllItems(@PageableDefault(size = 5) Pageable pageable) {
        Page<ItemResponse> page = itemService.getAllItems(pageable);
        System.out.println(page.getTotalElements());
        return itemService.getAllItems(pageable);
    }

    @PutMapping
    @ApiOperation(value = "Метод, обновляющий информацию о предмете")
    public void updateItem(@RequestBody ItemUpdate item) {
        itemService.updateItem(item);
    }

    @PostMapping
    @ApiOperation(value = "Метод, создающий информацию о предмете")
    public void createUser(@RequestBody ItemRequest item) {
        itemService.create(item);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Метод, удаляющий информацию о предмете")
    public void delete(@PathVariable BigDecimal id) {
        itemService.deleteItemById(id);
    }
}
