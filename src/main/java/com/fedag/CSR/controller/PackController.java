package com.fedag.CSR.controller;

import com.fedag.CSR.model.Pack;
import com.fedag.CSR.service.PackService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.management.MemoryUsage;
import java.math.BigDecimal;

@CrossOrigin(origins = "http://5.101.51.15/", maxAge = 3600)
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
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public ResponseEntity<?> getPack(@PathVariable BigDecimal id){
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
    public ResponseEntity<?> getAllPacks(@PageableDefault(size = 5) Pageable pageable) {
        Page<Pack> page = packService.findAll(pageable);
        return ResponseEntity.ok().body(page);
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
    public void createPack(@RequestBody String pack, @RequestPart MultipartFile file) throws IOException {
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
}