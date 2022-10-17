package com.fedag.CSR.controller;

import com.fedag.CSR.model.Item;
import com.fedag.CSR.service.ItemsWonService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/itemsWon")
@RequiredArgsConstructor
public class ItemsWonController {

    private final ItemsWonService itemsWonService;


    @GetMapping("sellAllItems/{id}")
    @ApiOperation(value = "Получение выигранных предметов по id баланса.",
            notes = "Предоставьте id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Предметы получены",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public void sellAllItems(@PathVariable BigDecimal id){
        itemsWonService.sellAllItemsByBalanceId(id);
    }

    @GetMapping("sellAnItem/{id}")
    @ApiOperation(value = "Продажа предмета на балансе",
            notes = "Предоставьте id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Предметы получены",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public void sellAnItem(@PathVariable BigDecimal id, @RequestHeader("Authorization") String token){
        itemsWonService.sellAnItemWonByUserIdAndItemId(id, token);
    }
}
