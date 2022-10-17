package com.fedag.CSR.repository;

import com.fedag.CSR.model.Balance;
import com.fedag.CSR.model.ItemsWon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ItemsWonRepository extends JpaRepository<ItemsWon, Long> {
    List<ItemsWon> findAllByBalancesId(BigDecimal id);

    ItemsWon findItemsWonByUsersIdAndItemsItemId(BigDecimal userId, BigDecimal itemId);
}
