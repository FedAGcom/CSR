package com.fedag.CSR.service.impl;

import com.fedag.CSR.service.SkinnfyService;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class SkinnyfyServiceImpl implements SkinnfyService {

    String url = "https://skinify.io/api/create-deposit";
    String depositUrl;
    RestTemplate restTemplate = new RestTemplate();
    @Override
    public void createDeposit() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.set("Token", "sometesttoken");
        Map<String, Object> requestParam = new HashMap<>();
        requestParam.put("deposit_id", 123123);
        requestParam.put("steam_id", 76561198117742847L);
        requestParam.put("trade_url_token", "4iF-0OCd");
        //requestParam.put("currency", "RUB");
        requestParam.put("min_amount", 200);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestParam, httpHeaders);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        if (response.getStatusCode() == HttpStatus.CREATED) {
            System.out.println("Request Successful");
            System.out.println(response.getBody());
        } else {
            System.out.println("Request Failed");
            System.out.println(response.getBody());
            System.out.println(response.getStatusCode().name());
        }
    }
}
