package com.fedag.CSR.service;

import com.fedag.CSR.model.Pack;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;

public interface PackService {
    Page<Pack> findAll(Pageable pageable);
    @Transactional
    Pack create(Pack pack) throws IOException;
    void deletePackById(BigDecimal id);
    @Transactional
    void deleteById(BigDecimal id);
    Pack findById(BigDecimal id);
}