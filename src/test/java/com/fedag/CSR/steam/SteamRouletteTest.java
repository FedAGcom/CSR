package com.fedag.CSR.steam;


import com.fedag.CSR.model.*;
import com.fedag.CSR.service.utils.SteamRoulette;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.*;

public class SteamRouletteTest {

    static Random random = new Random(47);
    private int userInPlusCount = 0;
    private List<Double> inPlusUserList = new ArrayList<>();

//    @BeforeAll
//    static void createAll(){
//        List<User> users = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            User user = new User();
//            users.add(user);
//        }
//        List<Balance> balances = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            Balance balance = new Balance();
//            balance.setUser(users.get(i));
//            users.get(i).setBalance(balance);
//            balances.add(balance);
//        }
//        List<Item> items = new ArrayList<>();
//        for (int i = 0; i < 1000; i++) {
//            Item item = new Item();
//            items.add(item);
//        }
//        List<Pack> packs = new ArrayList<>();
//        for (int i = 0; i < 30; i++) {
//            Pack pack = new Pack();
//            List<Item> itemsForPack = new ArrayList<>();
//            for (int j = 0; j < 30; j++) {
//                itemsForPack.add(items.get(random.nextInt(items.size())));
//            }
//            packs.add(pack);
//        }
//    }

    @Test
    void generateResult() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            User user = new User();
            user.setOpenCaseList(new ArrayList<>());
            users.add(user);

        }
        List<Balance> balances = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Balance balance = new Balance();
            balance.setUser(users.get(i));
            users.get(i).setBalance(balance);
            balances.add(balance);
        }
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < 30_000; i++) {
            Item item = new Item();
            if (i < 20_000){
                item.setPrice(new BigDecimal(random.nextInt(300))); // настроить
            } else if (i <= 25_000) {
                item.setPrice(new BigDecimal(random.nextInt(500) +200)); // настроить
            } else if (i <= 29_000) {
                item.setPrice(new BigDecimal(random.nextInt(300) + 500)); // настроить
            } else {
                item.setPrice(new BigDecimal(random.nextInt(300) + 700)); // настроить
            }
            items.add(item);
        }
        int countItem = 0;
        List<Pack> packs = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            Pack pack = new Pack();
            pack.setPrice(new BigDecimal(300)); // настроить
            List<Item> itemsForPack = new ArrayList<>();
            for (int j = 0; j < 30; j++) {
                Item item = items.get(random.nextInt(items.size()));
                itemsForPack.add(item);
                items.remove(item);
            }
            pack.setItems(itemsForPack);
            packs.add(pack);
        }
        for (int i = 0; i < 1000; i++) {
            SteamRoulette.generateResult(users.get(random.nextInt(users.size())),
                    packs.get(random.nextInt(packs.size())));
        }
        for (int i = 0; i < users.size(); i++) {
            double win = users.get(i).getOpenCaseList().stream()
                    .map(OpenCase::getItem).map(Item::getPrice)
                    .reduce((a, b) -> a.add(b)).orElseGet(() -> new BigDecimal(0)).doubleValue();

            double spent = users.get(i).getOpenCaseList().stream()
                    .map(OpenCase::getPack).map(Pack::getPrice)
                    .reduce((a, b) -> a.add(b)).orElseGet(() -> new BigDecimal(0)).doubleValue();
            if (spent < win) {
                userInPlusCount++;
                inPlusUserList.add(win/spent);
            }

        }
        System.out.println("profit: " + SteamRoulette.profit + " userWhoWinByWinChance: "
                + SteamRoulette.userWhoWinByWinChance );
        System.out.println("Юзера в +: " + userInPlusCount);
        inPlusUserList.forEach((a) -> System.out.print(a + " "));
    }

//    @ParameterizedTest(name = "Test: {index} - Value: {arguments}")
//    @MethodSource("createPairNumber")
//    void generateResultTest(User user, Pack pack) {
//        SteamRoulette.generateResult(user, pack);
//    }
//
//    private static Collection createPairNumber() {
//        Object[][] objects = new Object[1000][2];
//        Random random = new Random();
//        for (int i = 0; i < objects.length; i++) {
//            for (int j = 0; j < objects.length; j++) {
//                User user = new User();
//                user.setBalance(new Balance());
//                List<OpenCase> openCaseList = new ArrayList<>();
//                for (int k = 0; k < random.nextInt(30); k++) {
//                    openCaseList.add(new OpenCase());
//                }
//                user.setOpenCaseList(openCaseList);
//                objects[i][0] = user;
//                Pack pack = new Pack();
//                List<Item> items = new ArrayList<>();
//                for (int k = 0; k < 30; k++) {
//                    Item item = new Item();
//                    item.setPrice(BigDecimal.valueOf(random.nextInt(1000)));
//                    item.setPack(pack);
//                    items.add(item);
//                }
//                pack.setItems(items);
//                objects[i][0] = user;
//                objects[i][1] = pack;
//            }
//        }
//
//        return Arrays.asList(objects);
//    }
}
