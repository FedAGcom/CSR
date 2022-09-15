package com.fedag.CSR.controller;

import com.fedag.CSR.dto.response.ItemResponse;
import com.fedag.CSR.dto.response.PackResponse;
import com.fedag.CSR.service.ItemService;
import com.fedag.CSR.service.PackService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/v1/cases")
@RequiredArgsConstructor
@Tag(name = "Open case controller", description = "Opening cases")
public class OpenPackController {
    private final PackService packService;
    private final ItemService itemService;

    @PostMapping("/{title}")
    @ApiOperation(value = "Открытие кейса")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Кейс открыт",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    public ResponseEntity<String> openPack(@PathVariable String title) {
        PackResponse pack = packService.getPackByTitle(title);
        List<BigDecimal> listItemsId = pack.getPackItemsId();
        Random random = new Random();
        int num = random.nextInt(listItemsId.size());
        ItemResponse item = itemService.getItem(listItemsId.get(num));

        return ResponseEntity.ok().body("Вы выиграли: " + item.getTitle() + " стоимостью: " + item.getPrice());
    }
}