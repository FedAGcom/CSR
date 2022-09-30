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
        BigDecimal id = BigDecimal.valueOf(21);
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiLjgrjjg6fjg4vjg7wiLCJyb2xlIjoidXNlciIsImlhdCI6MTY2NDUzNjY2OSwiZXhwIjoxNjY1MTQxNDY5fQ.otmdhE4jvn3bkWnqvepu_MLTFsZAQuhc_3xVNLwvKJE";
        int round = 1_000;
        double pricePack = 1500;

        double sumPayIng = pricePack * round;
        double sumPayOut = 0;

        int appWin = 0;
        int userWin = 0;

        while (round > 0) {

            BigDecimal itemId = winChanceServiceImpl.spinCase(id, token);
            double priceItem = itemServiceImpl.getItem(itemId).getPrice();

            getIdPriceItem.add(String.format("Id item: %f price: %.2f", itemId, priceItem));

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


