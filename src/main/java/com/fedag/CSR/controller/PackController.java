package com.fedag.CSR.controller;

import com.fedag.CSR.dto.request.PackRequest;
import com.fedag.CSR.dto.response.PackResponse;
import com.fedag.CSR.dto.update.PackUpdate;
import com.fedag.CSR.mapper.PackMapper;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.*;
import java.math.BigDecimal;
import java.util.Optional;

import static org.springframework.http.HttpStatus.OK;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/packs")
@RequiredArgsConstructor
public class PackController {

    private final PackService packService;
    private final PackMapper packMapper;

    @GetMapping("/{id}")
    @ApiOperation(value = "Получение кейса по id.",
            notes = "Предоставьте id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Кейс получен",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public ResponseEntity<?> getPack(@PathVariable BigDecimal id) {
        return ResponseEntity.ok().body(packService.findById(id));
    }

    @GetMapping
    @ApiOperation(value = "Список всех кейсов.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список кейсов получен.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public Page<PackResponse> getAllPacks(@PageableDefault(size = 125) Pageable pageable) {
        return packService.findAll(pageable);
    }

    @PostMapping
    @ApiOperation(value = "Создание нового кейса.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Кейс создан.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public void createPack(@RequestPart String pack, @RequestPart MultipartFile file) throws IOException {
        packService.create(pack, file);
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
    @PreAuthorize("hasAuthority('admin')")
    public void delete(@PathVariable BigDecimal id) {
        packService.deletePackById(id);
    }

    //Тестовый метод на контроллер для проверки работы картинок Pack
    @GetMapping("test-image/{id}")
    @ApiOperation(value = "Получение картинки кейса",
            notes = "Предоставьте id кейса")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Картинка кейса получена",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
//    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public String getImage(@PathVariable BigDecimal id) {
        PackResponse packResponse = packService.findById(id);
        return "<img src='data:" + packResponse.getImageType() + ";base64," + packResponse.getImage() + "'/>";
    }

    @PutMapping
    public ResponseEntity<?> updatePack(@RequestPart String pack, @RequestPart MultipartFile file) throws IOException {
        return ResponseEntity.ok().body(packService.updatePack(pack, file));
    }
}