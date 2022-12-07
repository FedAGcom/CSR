package com.fedag.CSR.repository;

import com.fedag.CSR.model.PromoCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface PromoCodesRepository extends JpaRepository<PromoCode, BigDecimal> {
    PromoCode findPromoCodeByPromoCodeName(String promoCode);
    List<PromoCode> findAll();
    void deleteById(PromoCode promoCode);
}
