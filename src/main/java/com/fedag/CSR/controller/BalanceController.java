package com.fedag.CSR.controller;

import com.fedag.CSR.model.Balance;
import com.fedag.CSR.model.dto.request.BalanceRequest;
import com.fedag.CSR.model.dto.request.BalanceUpdateRequest;
import com.fedag.CSR.model.dto.response.BalanceResponse;
import com.fedag.CSR.model.mapper.BalanceMapper;
import com.fedag.CSR.service.BalanceService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * Rest Controller for class {@link Balance}.
 *
 * @author Kirill Soklakov
 * @since 2022-09-01
 * @version 1.1
 */
@RestController
@RequestMapping("/api/v1/balance")
@RequiredArgsConstructor
public class BalanceController {
    private final BalanceService balanceService;
    private final BalanceMapper balanceMapper;

    @GetMapping
    @ApiOperation(value = "Shows the list of all balances", response = Balance.class)
    public ResponseEntity<Page<BalanceResponse>> getAllBalances(@PageableDefault(size = 5)
                                                Pageable pageable) {
        Page<BalanceResponse> result = balanceService.findAll(pageable)
                .map(balanceMapper::modelToDto);
        return new ResponseEntity<>(result, OK);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Shows the specified balance.",
            notes = "Please provide an id.")
    public ResponseEntity<BalanceResponse> getBalance(@PathVariable int id) {
        BalanceResponse result = Optional.of(id)
                .map(balanceService::findById)
                .map(balanceMapper::modelToDto)
                .orElseThrow();
        return new ResponseEntity<>(result, OK);
    }

    @PostMapping
    @ApiOperation(value = "Creates a new balance.")
    public ResponseEntity<BalanceResponse> create(@RequestBody @Valid BalanceRequest balanceRequest) {
        BalanceResponse result = Optional.ofNullable(balanceRequest)
                .map(balanceMapper::dtoToModel)
                .map(balanceService::create)
                .map(balanceMapper::modelToDto)
                .orElseThrow();
        return new ResponseEntity<>(result, CREATED);
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "Updates the specified balance.")
    public ResponseEntity<BalanceResponse> update(@PathVariable Integer id,
                                                    @RequestBody @Valid BalanceUpdateRequest balanceRequestUpdate) {
        BalanceResponse result = Optional.ofNullable(balanceRequestUpdate)
                .map(balanceMapper::dtoToModel)
                .map(balance -> balanceService.update(id, balance))
                .map(balanceMapper::modelToDto)
                .orElseThrow();
        return new ResponseEntity<>(result, OK);
    }


    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deletes the specified balance.",
            notes = "Please provide an id.")
    public ResponseEntity<String> delete(@PathVariable int id) {
        balanceService.deleteById(id);
        return ResponseEntity.ok().body("Balance with ID = " + id + " was deleted.");
    }
}
