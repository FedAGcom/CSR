package com.fedag.CSR.repository;

import com.fedag.CSR.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ItemRepository extends JpaRepository<Item, BigDecimal> {
}
