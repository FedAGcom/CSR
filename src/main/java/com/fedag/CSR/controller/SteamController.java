package com.fedag.CSR.controller;

import com.fedag.CSR.service.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/steam")
@Tag(name = "Контроллер Steam", description = "Работа с функциями Steam")
public class SteamController {

    @Schema(name = "Сервис вещей",
            description = "Содержит имплементацию методов для работы с репозиторием")
    private final ItemService itemService;

//    @Schema(name = "Сервис для работы со Steam",
//            description = "Содержит имплементацию методов для работы с репозиторием")
//    private final SteamService steamService;

    @Operation(summary = "Добавление в приложение вещей с аккаунта Steam")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Вещи добавлены",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @GetMapping(value = "/add-all-items/{id}")
    public void addAllItemsFromUserSteamAccount(@PathVariable BigDecimal id) {
        itemService.addAllItems(id);
    }

//    @GetMapping(value = "/get-item-from-case/{pack_id}/{user_id}")
//    public void getItemFromCase(@PathVariable BigDecimal pack_id, @PathVariable BigDecimal user_id) {
//        steamService.getItemFromCase(pack_id, user_id);
//    }
}