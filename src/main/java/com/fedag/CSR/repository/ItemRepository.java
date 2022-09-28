package com.fedag.CSR.repository;

import com.fedag.CSR.dto.request.ItemRequest;
import com.fedag.CSR.model.Balance;
import com.fedag.CSR.model.Item;
import com.fedag.CSR.model.Pack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, BigDecimal> {
}