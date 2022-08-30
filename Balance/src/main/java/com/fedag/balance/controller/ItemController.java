//package com.fedag.balance.controller;
//
//import com.fedag.balance.model.Balance;
//import com.fedag.balance.model.Item;
//import com.fedag.balance.repository.BalanceRepository;
//import com.fedag.balance.repository.ItemRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//
//import javax.validation.Valid;
//import java.net.URI;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/v1/items")
//public class ItemController {
//    private final ItemRepository itemRepository;
//    private final BalanceRepository balanceRepository;
//
//    @Autowired
//    public ItemController(ItemRepository itemRepository, BalanceRepository balanceRepository) {
//        this.itemRepository = itemRepository;
//        this.balanceRepository = balanceRepository;
//    }
//
//    @PostMapping
//    public ResponseEntity<Item> create(@RequestBody @Valid Item item) {
//        Optional<Balance> optionalBalance = balanceRepository.findById(item.getBalance().getId());
//        if (!optionalBalance.isPresent()) {
//            return ResponseEntity.unprocessableEntity().build();
//        }
//
//        item.setBalance(optionalBalance.get());
//
//        Item savedItem = itemRepository.save(item);
//        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
//                .buildAndExpand(savedItem.getId()).toUri();
//
//        return ResponseEntity.created(location).body(savedItem);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Item> update(@RequestBody @Valid Item item, @PathVariable Integer id) {
//        Optional<Balance> optionalBalance = balanceRepository.findById(item.getBalance().getId());
//        if (!optionalBalance.isPresent()) {
//            return ResponseEntity.unprocessableEntity().build();
//        }
//
//        Optional<Item> optionalItem = itemRepository.findById(id);
//        if (!optionalItem.isPresent()) {
//            return ResponseEntity.unprocessableEntity().build();
//        }
//
//        item.setBalance(optionalBalance.get());
//        item.setId(optionalItem.get().getId());
//        itemRepository.save(item);
//
//        return ResponseEntity.noContent().build();
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Item> delete(@PathVariable Integer id) {
//        Optional<Item> optionalItem = itemRepository.findById(id);
//        if (!optionalItem.isPresent()) {
//            return ResponseEntity.unprocessableEntity().build();
//        }
//
//        itemRepository.delete(optionalItem.get());
//
//        return ResponseEntity.noContent().build();
//    }
//
//    @GetMapping
//    public ResponseEntity<Page<Item>> getAll(Pageable pageable) {
//        return ResponseEntity.ok(itemRepository.findAll(pageable));
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Item> getById(@PathVariable Integer id) {
//        Optional<Item> optionalItem = itemRepository.findById(id);
//        if (!optionalItem.isPresent()) {
//            return ResponseEntity.unprocessableEntity().build();
//        }
//
//        return ResponseEntity.ok(optionalItem.get());
//    }
//}
