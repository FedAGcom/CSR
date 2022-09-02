package com.fedag.CSR.steam;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.lukaspradel.steamapi.webapi.client.SteamWebApiClient;
import com.lukaspradel.steamapi.webapi.request.GetFriendListRequest;
import com.lukaspradel.steamapi.webapi.request.SteamWebApiRequest;
import com.lukaspradel.steamapi.webapi.request.builders.SteamWebApiRequestFactory;

import lombok.Data;
import lombok.ToString;
import org.apache.tomcat.util.json.JSONParser;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main2 {
    public static void main(String[] args) throws IOException {
//        SteamWebApiClient client = new SteamWebApiClient.SteamWebApiClientBuilder("F763327BB1CA8AFC6BEB4CC99BE9FB33").build();
      long start = System.currentTimeMillis();

        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "https://steamcommunity.com/id/cyglaif/inventory/json/730/2";

//        String fooResourceUrl
//                = "https://steamcommunity.com/id/cyglaif/inventory/json/570/2";

        long start2 = System.currentTimeMillis();
        ResponseEntity<JSONObject> response
                = restTemplate.getForEntity(fooResourceUrl, JSONObject.class);
//        Object obj = new JSONParser().parse(fooResourceUrl);
        String s = response.getBody().toJSONString();
//        System.out.println(s);


        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readValue(s, JsonNode.class); // парсинг текста
//        String message = rootNode.get("message").asText(); // получение строки из поля "message"

//        JsonNode childNode =  rootNode.get("rgInventory"); // получаем объект Place
//        System.out.println("dfdf");
//        System.out.println(childNode.get("24992003048"));


//        JsonNode childNode =  rootNode.get("rgDescriptions"); // получаем объект Place
//        System.out.println("dfdf");
//        System.out.println(childNode.get("1989288818_302028390"));

        JsonNode childNode =  rootNode.get("rgDescriptions");
//        System.out.println(childNode);
//        JsonNode childNode1 = childNode.get("1989288818_302028390");
////        System.out.println(childNode1);
//        JsonNode childNode2 = childNode1.get("classid");
////        System.out.println(childNode2);

        List<Test> result = new ArrayList<>();

        int i= 0;

        Iterator<JsonNode> iterator =  childNode.elements();
        while (iterator.hasNext()){
            JsonNode childNode3 = iterator.next();
            Test test = new Test();
            test.setClassid(childNode3.get("classid").toString());
            test.setInstanceid(childNode3.get("instanceid").toString());
            test.setName(childNode3.get("name").toString());
            result.add(test);
            i++;
//            System.out.println(childNode3);
        }
        System.out.println(i);
        System.out.println(System.currentTimeMillis() - start2);
        System.out.println(System.currentTimeMillis() - start);
        System.out.println(result);





//        JsonNode childNode1 =  childNode.get("24992003048"); // получаем объект Place
//        System.out.println(childNode1.asText());
////
//
//        JsonNode childNode2 =  childNode.get("name"); // получаем объект Place
//        System.out.println(childNode2.asText());


//        String place = childNode.get("name").asText(); // получаем строку из поля "name"
//        System.out.println(message + " " + place); // напечатает "Hi World!"
    }
}
@Data
@ToString
class Test{
    String classid;
    String instanceid;
    String name;
}
