package com.fedag.CSR.service;

import com.fedag.CSR.model.PromoCode;
import org.springframework.http.ResponseEntity;

public interface PromoCodesService {

    void save(PromoCode promoCode);

    ResponseEntity<?> checkPromoCode(String promoCode, String userToken);
}
