package com.fedag.CSR.controller;

import com.fedag.CSR.model.FrontParams;
import com.fedag.CSR.service.FrontParamsService;
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
@RequestMapping("/api/v1/front")
@RequiredArgsConstructor
public class FrontParamsController {
    private final FrontParamsService frontParamsService;

    @PutMapping
    @ApiOperation(value = "Обновление параметров в базе.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Фронт-параметры записаны.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PreAuthorize("hasAnyAuthority('admin')")
    public ResponseEntity<?> saveFrontParams(@RequestBody FrontParams frontParams) {
        return ResponseEntity.ok().body(frontParamsService.updateFrontParam(frontParams));
    }
}
