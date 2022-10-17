package com.fedag.CSR.controller;

import com.fedag.CSR.model.Balance;
import com.fedag.CSR.dto.request.BalanceRequest;
import com.fedag.CSR.dto.update.BalanceUpdate;
import com.fedag.CSR.dto.response.BalanceResponse;
import com.fedag.CSR.mapper.BalanceMapper;
import com.fedag.CSR.model.User;
import com.fedag.CSR.repository.UserRepository;
import com.fedag.CSR.service.BalanceService;
import com.fedag.CSR.service.UserService;
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

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

/**
 * Rest Controller for class {@link Balance}.
 *
 * @author Kirill Soklakov
 * @version 1.1
 * @since 2022-09-01
 */
@CrossOrigin(origins = "http://5.101.51.15/", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/balances")
@RequiredArgsConstructor
public class BalanceController {
    private final BalanceService balanceService;
    private final BalanceMapper balanceMapper;
    @GetMapping
    @ApiOperation(value = "Список всех балансов.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список балансов получен.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Page<BalanceResponse>> getAllBalances(@PageableDefault(size = 5)
                                                                Pageable pageable) {
        Page<BalanceResponse> result = balanceService.findAll(pageable)
                .map(balanceMapper::modelToDto);
        return new ResponseEntity<>(result, OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Получение баланса по id.",
            notes = "Предоставьте id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Баланс получен",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public ResponseEntity<BalanceResponse> getBalance(@PathVariable BigDecimal id) {
        BalanceResponse result = Optional.of(id)
                .map(balanceService::findById)
                .map(balanceMapper::modelToDto)
                .orElseThrow();
        return new ResponseEntity<>(result, OK);
    }

    @PostMapping
    @ApiOperation(value = "Создание нового баланса.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Баланс создан.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<BalanceResponse> create(@RequestBody @Valid BalanceRequest balanceRequest) {
        BalanceResponse result = Optional.ofNullable(balanceRequest)
                .map(balanceMapper::dtoToModel)
                .map(balanceService::create)
                .map(balanceMapper::modelToDto)
                .orElseThrow();
        return new ResponseEntity<>(result, CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Обновление баланса.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Баланс обновлен.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<BalanceResponse> update(@PathVariable BigDecimal id,
                                                  @RequestBody @Valid BalanceUpdate balanceRequestUpdate) {
        BalanceResponse result = Optional.ofNullable(balanceRequestUpdate)
                .map(balanceMapper::dtoToModel)
                .map(balance -> balanceService.update(id, balance))
                .map(balanceMapper::modelToDto)
                .orElseThrow();
        return new ResponseEntity<>(result, OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Удаление баланса",
            notes = "Предоставьте id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Баланс удален.",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<String> delete(@PathVariable BigDecimal id) {
        balanceService.deleteById(id);
        return ResponseEntity.ok().body("Balance with ID = " + id + " was deleted.");
    }

    @PostMapping("/soldAllItems/{id}")
    @ApiOperation(value = "Продажа всех предметов по id баланса")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Предметы проданы",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public ResponseEntity<?> soldAllItemsByBalanceId(@PathVariable BigDecimal id){
        return ResponseEntity.ok().body("Продано предметов на сумму: " + balanceService.soldAllItemsByBalanceId(id));
    }
}
