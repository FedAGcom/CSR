package com.fedag.CSR.service.utils;

import com.fedag.CSR.model.Item;
import com.fedag.CSR.model.OpenCase;
import com.fedag.CSR.model.Pack;
import com.fedag.CSR.model.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SteamRoulette {
    static Random random = new Random();
    static final double COEFFICIENT = 0.999; // 0.3 для 1 сборки
    final static double winChance = 0.30; //0.1 для 1 сборки не ставить больше 30
    public static double userWhoWinByWinChance = 0; //public для тестов
    public static double profit = 0; //public для тестов
    static double limitToIncreaseWinChance = 0;
    static final double DAILY_PROFIT = 15_000; // можно изменить название считаем по 10к
    static double counterLimitToIncreaseWinChance = 0;
    final static double superWinChance = 0.30;

    // временные счетчики
    private int userInPlusCount = 0;

    public static void generateResult(User user, Pack pack) {
        double userCoefficient;
        //проверка на первую покупку
        double userSpentMoney = user.getOpenCaseList().stream()
                .map(OpenCase::getItem).map(Item::getPrice)
                .reduce((a, b) -> a.add(b)).orElseGet(() -> new BigDecimal(0)).doubleValue();

        double userWintMoney = user.getOpenCaseList().stream()
                .map(OpenCase::getPack).map(Pack::getPrice)
                .reduce((a, b) -> a.add(b)).orElseGet(() -> new BigDecimal(0)).doubleValue();

        if (userSpentMoney != 0) {
            userCoefficient = userWintMoney / userSpentMoney;
        } else {
            userCoefficient = 0;
        }

        // узнали разницу коэффициентво для вычисления допустимой суммы
        double differenceCoefficient = COEFFICIENT - userCoefficient;
        double permissibleMoney = differenceCoefficient * (userSpentMoney + pack.getPrice().doubleValue());

        //проверка не набрали ли мы limitToIncreaseWinChance
        if (limitToIncreaseWinChance >= DAILY_PROFIT) {
            counterLimitToIncreaseWinChance = 10;
            limitToIncreaseWinChance = 0;
        }

        //дадим возможность выиграть не взирая на коэффициент
        List<Item> itemForSailToUser = new ArrayList<>(30);
        double userChance;
        if (counterLimitToIncreaseWinChance != 0) {
            userChance = random.nextInt(100 / (int) (superWinChance * 100));
            counterLimitToIncreaseWinChance--;
        } else {
            userChance = random.nextInt(100 / (int) (winChance * 100));
        }

        List<Item> items = pack.getItems();
        if (userChance == 1) {
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getPrice().doubleValue() > permissibleMoney) {
                    itemForSailToUser.add(items.get(i));
                }
            }
            //если в итоге список пуст разыгрваем все айтемы
            if (itemForSailToUser.isEmpty()) {
                Item mostExpensive = items.get(0);
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).getPrice().doubleValue() > mostExpensive.getPrice().doubleValue()) {
                        mostExpensive = items.get(i);
                    }
                }
                itemForSailToUser.add(mostExpensive); //подумать
            } else { //значит список после добавления итема
                // свыше допустимой цены есть и увеличивем счетчик
                userWhoWinByWinChance++;
            }
        } else {

            //если не повезло проходим по списку итемов в кейсе и выбираем допустимые
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getPrice().doubleValue() < permissibleMoney) {
                    itemForSailToUser.add(items.get(i));
                }
            }
            //если в итоге список пуст (значит у юзера маленькая допустимая сумма)
            // отдаем ему самую нищебродскую
            Item mostCheap = items.get(0);
            if (itemForSailToUser.isEmpty()) {
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).getPrice().doubleValue() < mostCheap.getPrice().doubleValue()) {
                        mostCheap = items.get(i);
                    }
                }
                itemForSailToUser.add(mostCheap);
            }
        }

//         добавляем юзеру итем, рандомно выбрав его из результирующего листа
        int randomItemIndex;
        if (itemForSailToUser.size() == 1) {
            randomItemIndex = 0;
        } else {
            randomItemIndex = (random.nextInt(itemForSailToUser.size()));
        }
        Item itemForSale = itemForSailToUser.get(randomItemIndex);
        user.getBalance().getItems().add(itemForSale);
        OpenCase openCase = new OpenCase();
        openCase.setDate(LocalDateTime.now());
        openCase.setItem(itemForSale);
        openCase.setUser(user);
        openCase.setPack(pack);
        user.getOpenCaseList().add(openCase);
        limitToIncreaseWinChance = limitToIncreaseWinChance
                + pack.getPrice().doubleValue() - itemForSale.getPrice().doubleValue();
        profit = profit + pack.getPrice().doubleValue() - itemForSale.getPrice().doubleValue();
//        System.out.println("User в +");
    }

}
