package com.fedag.CSR.repository;

import com.fedag.CSR.enums.ItemsWonStatus;
import com.fedag.CSR.model.ItemsWon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ItemsWonRepository extends JpaRepository<ItemsWon, Long> {
    List<ItemsWon> findAllByBalancesIdAndItemsWonStatus(BigDecimal id, ItemsWonStatus itemsWonStatus);

    ItemsWon findTopItemsWonByUsersIdAndItemsItemIdAndItemsWonStatus(BigDecimal userId, BigDecimal itemId, ItemsWonStatus itemsWonStatus);

    List<ItemsWon> findAllByUsersIdAndItemsWonStatus(BigDecimal userId, ItemsWonStatus itemsWonStatus);

    List<ItemsWon> findAllByUsersId(BigDecimal userId);
    @Query(value = "SELECT * FROM items_won ORDER BY pack_opening_timestamp DESC LIMIT 10", nativeQuery = true)
    List<ItemsWon> getLastItemsWonLimit10();
    @Query(value = "SELECT * FROM items_won ORDER BY item_price DESC LIMIT 10", nativeQuery = true)
    List<ItemsWon> getTheBestItemsWonLimit10();
}
