package com.fedag.CSR.service.impl;

import com.fedag.CSR.model.Balance;
import com.fedag.CSR.model.PromoCode;
import com.fedag.CSR.model.User;
import com.fedag.CSR.repository.BalanceRepository;
import com.fedag.CSR.repository.PromoCodesRepository;
import com.fedag.CSR.repository.UserRepository;
import com.fedag.CSR.service.PromoCodesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PromoCodesServiceImpl implements PromoCodesService {

    private final BalanceRepository balanceRepository;
    private final PromoCodesRepository promoCodesRepository;
    private final UserRepository userRepository;

    @Override
    public void save(PromoCode promoCode) {
        promoCodesRepository.save(promoCode);
    }

    @Override
    public ResponseEntity<?> checkPromoCode(String promoCode, String userToken) {

        List<PromoCode> allPromoCodes = promoCodesRepository.findAll();
        List<String> allNames = new ArrayList<>();

        for (PromoCode iterablePromo : allPromoCodes) {
            allNames.add(iterablePromo.getPromoCodeName());
        }
        if (allNames.contains(promoCode)) {
            PromoCode requestPromo = promoCodesRepository.findPromoCodeByPromoCodeName(promoCode);
            if(requestPromo.getAmount() == 0){
                promoCodesRepository.delete(requestPromo);
            }
            if (requestPromo.getAmount() > 0 && requestPromo.getExpireDate().isAfter(LocalDate.now())) {
                Optional<User> user = userRepository.findUserByConfirmationToken(userToken);
                Balance balance = user.get().getBalance();
                double balanceCoins = balance.getCoins();
                double requestCoins = balanceCoins + requestPromo.getCoins();
                balance.setCoins(requestCoins);
                Long usageAmount = requestPromo.getAmount();
                Long finalAmount = usageAmount - 1;
                requestPromo.setAmount(finalAmount);
                balanceRepository.save(balance);
                promoCodesRepository.save(requestPromo);

            } else {
                return ResponseEntity.ok("Промокод недействителен");
            }
            return ResponseEntity.ok("Баланс успешно пополнен");
        }
        return ResponseEntity.ok("Промокод не найден");
    }
}
