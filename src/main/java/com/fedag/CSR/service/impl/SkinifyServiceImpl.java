package com.fedag.CSR.service.impl;

import com.fedag.CSR.enums.DepositStatus;
import com.fedag.CSR.exception.EntityNotFoundException;
import com.fedag.CSR.model.Balance;
import com.fedag.CSR.model.Deposit;
import com.fedag.CSR.model.DepositGlobal;
import com.fedag.CSR.model.User;
import com.fedag.CSR.repository.BalanceRepository;
import com.fedag.CSR.repository.DepositGlobalRepository;
import com.fedag.CSR.repository.DepositRepository;
import com.fedag.CSR.repository.UserRepository;
import com.fedag.CSR.service.SkinifyService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SkinifyServiceImpl implements SkinifyService {
    private final UserRepository userRepository;
    private final BalanceRepository balanceRepository;
    private final DepositRepository depositRepository;
    private final DepositGlobalRepository depositGlobalRepository;
    String depositUrl = "https://skinify.io/api/create-deposit";
    BigDecimal balanceId;
    String amountUrl = "https://skinify.io/api/deposit-status?transaction_id=";
    @Value("${skinify.token}")
    String token;
    RestTemplate restTemplate = new RestTemplate();

    @Override
    public String createDeposit(String userToken) {
        DepositGlobal depositGlobalValue = depositGlobalRepository.findById(1L).orElseThrow(
                () -> new EntityNotFoundException("Не найдена глобальная переменная globalDeposit"));

        Optional<User> user = userRepository.findUserByConfirmationToken(userToken);
        user.ifPresent(value -> balanceId = value.getBalance().getId());

        String stringDepositId = String.valueOf(depositGlobalValue.getGlobal_id());
        HttpHeaders depositHttpHeaders = new HttpHeaders();
        depositHttpHeaders.setContentType(MediaType.APPLICATION_JSON);
        depositHttpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        depositHttpHeaders.set("Token", token);

        Map<String, Object> depositRequestParam = new HashMap<>();
        depositRequestParam.put("deposit_id", stringDepositId);
        depositRequestParam.put("min_amount", 2);
        depositRequestParam.put("currency", "rub");

        HttpEntity<Map<String, Object>> depositRequest = new HttpEntity<>(depositRequestParam, depositHttpHeaders);
        ResponseEntity<String> depositResponse = restTemplate.postForEntity(depositUrl, depositRequest, String.class);

        String bodyURL = depositResponse.getBody();
        JSONObject jsonObject = new JSONObject(bodyURL);
        HttpHeaders amountHttpHeaders = new HttpHeaders();
        amountHttpHeaders.setContentType(MediaType.APPLICATION_JSON);
        amountHttpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        amountHttpHeaders.set("Token", token);

        String stringUrl = (String) jsonObject.get("url");


        Deposit newDeposit = new Deposit();
        Integer transactionId = (Integer) jsonObject.get("transaction_id");
        newDeposit.setTransactionId(Long.parseLong(String.valueOf(transactionId)));
        newDeposit.setDeposit(depositGlobalValue.getGlobal_id());
        newDeposit.setStatus(DepositStatus.PENDING);
        newDeposit.setUser(user.get());
        depositRepository.save(newDeposit);
        depositGlobalRepository.save(new DepositGlobal(1L, depositGlobalValue.getGlobal_id() + 1));
        System.out.println(jsonObject);
        return stringUrl;
    }

//    public DepositStatus checkDepositStatus(Long depositId, String userToken) {
//        Deposit currentDeposit = depositRepository.findByDeposit(depositId).orElseThrow(
//                () -> new EntityNotFoundException("Не найден deposit с id " + depositId));
//
////        DepositGlobal depositGlobalValue = depositGlobalRepository.findById(1L).orElseThrow(
////                () -> new EntityNotFoundException("Не найдена глобальная переменная globalDeposit"));
//
//        Optional<User> user = userRepository.findUserByConfirmationToken(userToken);
//        user.ifPresent(value -> balanceId = value.getBalance().getId());
//        System.out.println(user.get().getId());
//
//        HttpHeaders amountHttpHeaders = new HttpHeaders();
//        amountHttpHeaders.setContentType(MediaType.APPLICATION_JSON);
//        amountHttpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//        amountHttpHeaders.set("Token", token);
//
//        HttpEntity<Void> amountHttpEntity = new HttpEntity<>(amountHttpHeaders);
//
//        String hardCodedUrl = "https://skinify.io/api/deposit-status?deposit_id=";
//        String finalAmountUrl = hardCodedUrl + currentDeposit.getDeposit();
//        ResponseEntity<String> amountResponse = restTemplate.exchange(finalAmountUrl, HttpMethod.GET, amountHttpEntity, String.class);
//
//        JSONObject infoJsonObject = new JSONObject(amountResponse.getBody());
//        JSONObject deposit = (JSONObject) infoJsonObject.get("deposit");
//        String status = (String) deposit.get("status");
//
//        if (status.equals("success")) {
//            String amount = (String) deposit.get("amount");
//            Optional<Balance> balance = balanceRepository.findById(balanceId);
//            double coins = balance.get().getCoins();
//            coins += Double.parseDouble(amount);
//            balance.get().setCoins(coins);
//            balanceRepository.save(balance.get());
//
//            currentDeposit.setStatus(DepositStatus.SUCCESS);
////            depositGlobalRepository.save(new DepositGlobal(1L, depositGlobalValue.getGlobal_id() + 1));
//        } else if (status.equals("pending")) {
//            currentDeposit.setStatus(DepositStatus.PENDING);
//            System.out.println("Пользователь ожидает оплаты");
//        } else if (status.equals("failed")) {
//            currentDeposit.setStatus(DepositStatus.ERROR);
//            System.out.println("Оплата провалилась");
////            depositGlobalRepository.save(new DepositGlobal(1L, depositGlobalValue.getGlobal_id() + 1));
//        }
//        depositRepository.save(currentDeposit);
//
//        return currentDeposit.getStatus();
//    }

    public DepositStatus checkSuccessStatus(String depositId, String steamId,
                                             String amount) {


        Deposit currentDeposit = depositRepository.findByDeposit(Long.valueOf(depositId)).orElseThrow(
                () -> new EntityNotFoundException("Не найден deposit с id " + depositId));
        Optional<User> user = userRepository.findBySteamId(steamId);
        user.ifPresent(value -> balanceId = value.getBalance().getId());
        System.out.println(user.get().getId());

        Optional<Balance> balance = balanceRepository.findById(balanceId);
        Double coins = balance.get().getCoins();
        coins += Double.parseDouble(amount);
        balance.get().setCoins(coins);
        balanceRepository.save(balance.get());
        currentDeposit.setStatus(DepositStatus.SUCCESS);
        depositRepository.save(currentDeposit);


        return currentDeposit.getStatus();
    }


    public DepositStatus checkFailedStatus(String depositId) {

        Deposit currentDeposit = depositRepository.findByDeposit(Long.valueOf(depositId)).orElseThrow(
                () -> new EntityNotFoundException("Не найден deposit с id " + depositId));

        currentDeposit.setStatus(DepositStatus.ERROR);
        depositRepository.save(currentDeposit);

        return currentDeposit.getStatus();
    }
}

