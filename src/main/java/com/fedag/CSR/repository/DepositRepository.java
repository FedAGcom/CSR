package com.fedag.CSR.repository;

import com.fedag.CSR.model.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;


public interface DepositRepository extends JpaRepository<Deposit, Long> {
    Optional<Deposit> findByDeposit(Long deposit);
}
