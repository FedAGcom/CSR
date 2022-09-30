package com.fedag.CSR;

import com.fedag.CSR.service.impl.ItemServiceImpl;
import com.fedag.CSR.service.impl.WinChanceServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class WinChanceServiceTest {
    @Autowired
    private WinChanceServiceImpl winChanceServiceImpl;
    @Autowired
    private ItemServiceImpl itemServiceImpl;

    @Test
    public void testWinChanceService() {
        List<String> getIdPriceItem = new ArrayList<>();
        long id = 1L;
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKQUNLIEJCVVRUTEFORCIsInJvbGUiOiJ1c2VyIiwiaWF0IjoxNjY0NTIwMjY1LCJleHAiOjE2NjUxMjUwNjV9.npdv5I6ttg-Irvd7UY2yJWM9KLJUjMZrtNww0OxZxNg";
        int round = 1_000;
        double pricePack = 30;

        double sumPayIng = pricePack * round;
        double sumPayOut = 0;

        int appWin = 0;
        int userWin = 0;

        while (round > 0) {

            long itemId = winChanceServiceImpl.spinCase(id, token);
            double priceItem = itemServiceImpl.getItem(BigDecimal.valueOf(itemId)).getPrice();

            getIdPriceItem.add(String.format("Id item: %d price: %.2f", itemId, priceItem));

            sumPayOut += priceItem;

            if (priceItem > pricePack) userWin++;
            else appWin++;
            round--;

        }
        getIdPriceItem.forEach(log::info);

        log.info(sumPayIng > sumPayOut ? "App WIN" : "User WIN");
        log.info(String.format("AppWin = %,d, UserWin = %,d", appWin, userWin));
        log.info(String.format("Потрачено %,.2f. Выплачено %,.2f", sumPayIng, sumPayOut));
    }
}


