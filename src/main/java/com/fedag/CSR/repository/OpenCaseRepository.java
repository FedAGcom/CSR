package com.fedag.CSR.repository;

import com.fedag.CSR.model.OpenCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface OpenCaseRepository extends JpaRepository<OpenCase, BigDecimal> {
}
