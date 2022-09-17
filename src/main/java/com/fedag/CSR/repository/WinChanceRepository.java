package com.fedag.CSR.repository;

import com.fedag.CSR.model.WinChance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface WinChanceRepository extends JpaRepository<WinChance, Long> {

    @Query("SELECT p FROM WinChance p WHERE p.packId=:packId")
    List<WinChance> getWinChanceByPackId(Long packId);
}
