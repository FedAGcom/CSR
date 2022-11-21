package com.fedag.CSR.controller;

import com.fedag.CSR.model.Support;
import com.fedag.CSR.service.SupportService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/support")
@RequiredArgsConstructor
public class SupportController {

    private final SupportService supportService;


    @GetMapping("/{id}")
    @ApiOperation(value = "Получение сообщения в поддержку по id.",
            notes = "Предоставьте id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Сообщение получено",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> getMessage(@PathVariable Long id) {
        return ResponseEntity.ok().body(supportService.findById(id));
    }

    @GetMapping
    @ApiOperation(value = "Получение всех сообщений в службу поддержки")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Сообщения получены",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> getAllMessages() {
        return ResponseEntity.ok().body(supportService.findAll());
    }

    @GetMapping("/find-by-email/{email}")
    @ApiOperation(value = "Получение сообщения в поддержку по e-mail.",
            notes = "Предоставьте e-mail.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Сообщение получено",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> getMessageByEmail(@PathVariable String email) {
        return ResponseEntity.ok().body(supportService.findByEmail(email));
    }

    @PostMapping
    @ApiOperation(value = "Создание сообщения в поддержку по e-mail.", notes = "Предоставьте e-mail, тему сообщения, " +
            "текст сообщения, изображение")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Сообщение создано",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    public ResponseEntity<?> createMessageToSupport(@RequestBody Support support) {
        return ResponseEntity.ok().body(supportService.create(support));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Удаление сообщения в поддержку по id.",
            notes = "Предоставьте id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Сообщение удалено",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PreAuthorize("hasAnyAuthority('admin')")
    public void deleteMessage(@PathVariable Long id) {
        supportService.deleteById(id);
    }
}