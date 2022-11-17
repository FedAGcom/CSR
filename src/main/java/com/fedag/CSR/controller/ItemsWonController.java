package com.fedag.CSR.controller;

import com.fedag.CSR.service.ItemsWonService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/itemsWon")
@RequiredArgsConstructor
public class ItemsWonController {

    private final ItemsWonService itemsWonService;


    @GetMapping("sellAllItems/{id}")
    @ApiOperation(value = "Продажа всех выигранных предметов по id баланса.",
            notes = "Предоставьте id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Предметы получены",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public void sellAllItems(@PathVariable BigDecimal id) {
        itemsWonService.sellAllItemsByBalanceIdAndItemsWonStatus(id);
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
    public void sellAnItem(@PathVariable BigDecimal id, @RequestHeader("Authorization") String token) {
        itemsWonService.sellAnItemWonByUserIdAndItemId(id, token);
    }

    @GetMapping("/get-last-items-won")
    @ApiOperation(value = "Получение последних выигранных предметов",
            notes = "Предоставьте id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Предметы получены",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    public ResponseEntity<?> getLastItemsWon(){
        return ResponseEntity.ok().body(itemsWonService.getLastWiningItems());
    }

    @GetMapping("/get-the-best-items-won")
    @ApiOperation(value = "Получение лучших выигранных предметов",
            notes = "Предоставьте id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Предметы получены",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    public ResponseEntity<?> getTheBestItemsWon(){
        return ResponseEntity.ok().body(itemsWonService.getTheBestWiningItems());
    }

    @GetMapping("/count")
    @ApiOperation(value = "Получение количества открытых кейсов.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Количество получено",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public long totalPacksOpened() {
        return itemsWonService.totalPacksOpened();
    }
}
