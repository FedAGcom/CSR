package com.fedag.CSR.repository;

import com.fedag.CSR.model.BalanceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *  Jpa repository for model {@link BalanceItem}. Created for testing code
 *  @author Kirill Soklakov
 *  @version 1.1
 */
@Repository
public interface BalanceItemRepository extends JpaRepository<BalanceItem, Integer> {
}
