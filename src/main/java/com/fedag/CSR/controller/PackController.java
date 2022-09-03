package com.fedag.CSR.controller;

import com.fedag.CSR.dto.request.PackRequest;
import com.fedag.CSR.dto.response.PackResponse;
import com.fedag.CSR.dto.update.PackUpdate;
import com.fedag.CSR.service.PackService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/packs")
@RequiredArgsConstructor
public class PackController {

    private final PackService packService;

    @GetMapping("/{id}")
    @ApiOperation(value = "Получение кейса по id.",
            notes = "Предоставьте id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Кейс получен",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    public PackResponse getPack(@PathVariable BigDecimal id){
        return packService.getPack(id);
    }

    @GetMapping
    @ApiOperation(value = "Список всех кейсов.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список кейсов получен.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    public Page<PackResponse> getAllPacks(@PageableDefault(size = 5) Pageable pageable) {
        Page<PackResponse> page = packService.getAllPacks(pageable);
        return packService.getAllPacks(pageable);
    }

    @PutMapping
    @ApiOperation(value = "Обновление кейса.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Кейс обновлен.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    public void updatePack(@RequestBody PackUpdate pack) {
        packService.updatePack(pack);
    }

    @PostMapping
    @ApiOperation(value = "Создание нового кейса.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Предмет создан.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    public void createPack(@RequestBody PackRequest pack) {
        packService.create(pack);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Удаление кейса.",
            notes = "Предоставьте id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Предмет удален.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    public void delete(@PathVariable BigDecimal id) {
        packService.deletePackById(id);
    }
}