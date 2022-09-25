package com.fedag.CSR.service.impl;

import com.fedag.CSR.exception.NotEnoughCoinsToBuyAPackException;
import com.fedag.CSR.model.*;
import com.fedag.CSR.repository.ItemRepository;
import com.fedag.CSR.repository.UserRepository;
import com.fedag.CSR.repository.WinChanceRepository;
import com.fedag.CSR.service.ItemService;
import com.fedag.CSR.service.PackService;
import com.fedag.CSR.service.WinChanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class WinChanceServiceImpl implements WinChanceService {

    private final WinChanceRepository winChanceRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ItemService itemService;
    private final PackService packService;

    @Override
    public Long spinCase(Long id, String userToken) {
        List<WinChance> listWinChance = winChanceRepository.getWinChanceByPackId(id);
        int total = 0;
        Pack pack = packService.findById(BigDecimal.valueOf(id));

        Optional<User> user = userRepository.findUserByConfirmationToken(userToken);
        Balance balance = user.get().getBalance();

        if (pack.getPrice() <= balance.getCoins()) {
            double difference = balance.getCoins() - pack.getPrice();
            balance.setCoins(difference);

            for (int i = 0; i < listWinChance.size(); i++) {
                double modifiedWinChance = listWinChance.get(i).getWinChance() * 10_000;
                listWinChance.get(i).setWinChance(modifiedWinChance);
                total = total + (int) modifiedWinChance;
            }
            Random random = new Random();
            double value = random.nextInt(total);

            double counterLeft = 0;
            double counterRight = listWinChance.get(0).getWinChance();

            for (int i = 0; i < listWinChance.size(); i++) {
                if (value >= counterLeft && value <= counterRight) {
                    Long wonItemId = listWinChance.get(i).getItemId();
                    Item item = itemService.getItem(BigDecimal.valueOf(wonItemId));
                    item.setPack(null);
                    item.setBalance(balance);
                    itemRepository.save(item);
                    return listWinChance.get(i).getItemId();
                }
                counterLeft = counterRight;
                counterRight = counterRight + listWinChance.get(i).getWinChance();
            }
            Long wonItemId = listWinChance.get(listWinChance.size() - 1).getItemId();
            Item item = itemService.getItem(BigDecimal.valueOf(wonItemId));
            item.setPack(null);
            item.setBalance(balance);
            itemRepository.save(item);
            return listWinChance.get(listWinChance.size() - 1).getItemId();
        } else {
            throw new NotEnoughCoinsToBuyAPackException("There's not enough coins on your account.");
        }
    }
}
