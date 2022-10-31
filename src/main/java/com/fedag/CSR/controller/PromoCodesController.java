package com.fedag.CSR.controller;

import com.fedag.CSR.model.PromoCode;
import com.fedag.CSR.repository.PromoCodesRepository;
import com.fedag.CSR.service.PromoCodesService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.http.HttpResponse;


@Controller
@RequestMapping("/api/v1/promo-codes")
@RequiredArgsConstructor
public class PromoCodesController {

    private final PromoCodesService promoCodesService;


    @PostMapping("/createPromo")
    @ApiOperation(value = "Создание промокода")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Промокод создан",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public ResponseEntity<?> createPromo(@RequestBody PromoCode promoCode) {
        promoCodesService.save(promoCode);
        return ResponseEntity.ok().body(promoCode);
    }


    @GetMapping("/checkPromo")
    @ApiOperation(value = "Проверка промокода")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Промокод валиден",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public ResponseEntity<?> checkPromo(@RequestParam(name = "promo") String promoCode, @RequestHeader("Authorization") String userToken) {
        return promoCodesService.checkPromoCode(promoCode, userToken);
    }
}
