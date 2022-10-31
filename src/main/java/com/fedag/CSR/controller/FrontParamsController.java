package com.fedag.CSR.controller;

import com.fedag.CSR.model.FrontParams;
import com.fedag.CSR.repository.FrontParamsRepository;
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


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/front")
@RequiredArgsConstructor
public class FrontParamsController {
    private final FrontParamsService frontParamsService;
    @GetMapping("/{id}")
    @ApiOperation(value = "Получение фронт-параметров по id.",
            notes = "Предоставьте id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Фронт-параметры получены",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
//    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public ResponseEntity<?> getFrontParams(@PathVariable Long id){
        return ResponseEntity.ok().body(frontParamsService.getTheFrontParamsById(id));
    }


    @PostMapping
    @ApiOperation(value = "Сохранение параметров в базу.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Фронт-параметры записаны.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
//    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public void saveFrontParams(@RequestBody FrontParams frontParams) {
        frontParamsService.createFrontParams(frontParams);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Удаление фронт-параметров по id.",
            notes = "Предоставьте id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Фронт-параметры удалены.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
//    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public ResponseEntity<?> deleteFrontParams(@PathVariable Long id) {
        frontParamsService.deleteFrontParams(id);
        return ResponseEntity.ok().body("Фронт-параметры с id " + id + " успешно удалены");
    }
}
