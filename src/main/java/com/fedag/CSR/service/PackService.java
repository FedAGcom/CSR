package com.fedag.CSR.service;

import com.fedag.CSR.dto.response.PackResponse;
import com.fedag.CSR.model.Pack;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;

public interface PackService {
    Page<Pack> findAll(Pageable pageable);
    @Transactional
    Pack create(String pack) throws IOException;
    void deletePackById(BigDecimal id);
    @Transactional
    void deleteById(BigDecimal id);
    PackResponse findById(BigDecimal id);
    Pack findPackById(BigDecimal id);
}