package com.fedag.CSR.repository;

import com.fedag.CSR.model.Pack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface PackRepository extends JpaRepository<Pack, BigDecimal> {
}
