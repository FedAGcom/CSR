package com.fedag.CSR.repository;


import com.fedag.CSR.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Jpa repository for model {@link Balance}
 *
 * @author Kirill Soklakov
 * @version 1.1
 */
@Repository
public interface BalanceRepository extends JpaRepository<Balance, BigDecimal> {

    List<Balance> findAllByUserId(BigDecimal id);
}
