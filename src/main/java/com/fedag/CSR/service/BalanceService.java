package com.fedag.CSR.service;

import com.fedag.CSR.model.Balance;
import com.fedag.CSR.mapper.BalanceMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

/**
 * class CommentMapperImpl is implementation of {@link BalanceMapper} interface.
 *
 * @author Kirill Soklakov
 * @since 2022-09-01
 */

public interface BalanceService {
    Balance findById(BigDecimal id);

    Page<Balance> findAll(Pageable pageable);

    Balance create(Balance companyDto);

    Balance update(BigDecimal id, Balance companyDto);

    void deleteById(BigDecimal id);

}
