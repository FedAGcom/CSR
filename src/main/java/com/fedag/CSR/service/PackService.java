package com.fedag.CSR.service;

import com.fedag.CSR.dto.request.PackRequest;
import com.fedag.CSR.dto.response.PackResponse;
import com.fedag.CSR.dto.update.PackUpdate;
import com.fedag.CSR.model.Pack;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface PackService {
    Page<PackResponse> getAllPacks(Pageable pageable);

    Pack create(PackRequest pack);

    Pack updatePack(PackUpdate pack);

    void deletePackById(BigDecimal id);

    PackResponse getPack(BigDecimal id);
}
