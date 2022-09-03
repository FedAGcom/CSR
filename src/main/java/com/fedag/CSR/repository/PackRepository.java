package com.fedag.CSR.repository;

import com.fedag.CSR.model.Pack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface PackRepository extends JpaRepository<Pack, BigDecimal> {
}
