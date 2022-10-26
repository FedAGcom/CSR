package com.fedag.CSR.service;

import com.fedag.CSR.dto.response.PackResponse;
import com.fedag.CSR.model.Pack;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

public interface PackService {
    Page<PackResponse> findAll(Pageable pageable);

    @Transactional
    Pack create(String pack) throws IOException;

    void deletePackById(BigDecimal id);

    @Transactional
    void deleteById(BigDecimal id);

    PackResponse findById(BigDecimal id);

    Pack findPackById(BigDecimal id);

    Map<String, Object> updatePack(String pack) throws IOException;
}