package com.fedag.balance.controller;

import com.fedag.balance.model.Balance;
import com.fedag.balance.repository.ItemRepository;
import com.fedag.balance.repository.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;
//Rest contorller для сущности ballance, на CRUD операции
@RestController
@RequestMapping("/api/v1/balance")
public class BalanceController {
    private final BalanceRepository balanceRepository;
    private final ItemRepository itemRepository;

    @Autowired
    public BalanceController(BalanceRepository balanceRepository, ItemRepository itemRepository) {
        this.balanceRepository = balanceRepository;
        this.itemRepository = itemRepository;
    }

    @PostMapping
    public ResponseEntity<Balance> create(@Valid @RequestBody Balance balance) {
        Balance savedBalance = balanceRepository.save(balance);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedBalance.getId()).toUri();

        return ResponseEntity.created(location).body(savedBalance);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Balance> update(@PathVariable Integer id, @Valid @RequestBody Balance balance) {
        Optional<Balance> optionalBalance = balanceRepository.findById(id);
        if (!optionalBalance.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        balance.setId(optionalBalance.get().getId());
        balanceRepository.save(balance);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Balance> delete(@PathVariable Integer id) {
        Optional<Balance> optionalBalance = balanceRepository.findById(id);
        if (!optionalBalance.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        balanceRepository.delete(optionalBalance.get());

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Balance> getById(@PathVariable Integer id) {
        Optional<Balance> optionalBalance = balanceRepository.findById(id);
        if (!optionalBalance.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(optionalBalance.get());
    }

    @GetMapping
    public ResponseEntity<Page<Balance>> getAll(Pageable pageable) {
        return ResponseEntity.ok(balanceRepository.findAll(pageable));
    }
}
