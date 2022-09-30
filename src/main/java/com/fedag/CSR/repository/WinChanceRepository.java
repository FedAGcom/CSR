package com.fedag.CSR.repository;

import com.fedag.CSR.model.WinChance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface WinChanceRepository extends JpaRepository<WinChance, Long> {
    @Query("SELECT p FROM WinChance p WHERE p.pack.id=:packId")
    List<WinChance> getWinChanceByPackId(BigDecimal packId);
}
