package com.fedag.CSR.repository;

import com.fedag.CSR.enums.ItemsWonStatus;
import com.fedag.CSR.enums.PackStatus;
import com.fedag.CSR.model.ItemsWon;
import com.fedag.CSR.model.Pack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface PackRepository extends JpaRepository<Pack, BigDecimal> {
    List<Pack> findAllByStatus(PackStatus status);

}
