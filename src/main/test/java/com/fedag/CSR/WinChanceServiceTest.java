package com.fedag.CSR;

import com.fedag.CSR.service.impl.WinChanceServiceImpl;


import org.junit.Test;
import org.junit.runner.RunWith;

//
//import static com.jayway.restassured.RestAssured.given;
//
//@ExtendWith(MockitoExtension.class)
//public class WinChanceControllerTest {
//
//    @RepeatedTest(10)
//    public void spinCaseTest() {
//        Response response = given().
//                when().get("http://localhost:8080/api/v1/spin/2")
//                .then().contentType(ContentType.JSON)
//                .extract().response();
//
//        System.out.println(response);
//    }
//}


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WinChanceServiceTest {

    @Autowired
    private WinChanceServiceImpl winChanceServiceImpl;

    @Test
    public void test() {
        long id = 2L;
        int counter = 0;
        int counterLowestId = 0;
        int counterHighestId = 0;
        List<Long> list = new ArrayList<>();

        for (int i = 0; i < 100_00; i++) {
          long itemId =  winChanceServiceImpl.spinCase(id);
          if (itemId == 131) counterLowestId++;
          if (itemId == 132) counterHighestId++;
          list.add(itemId);
        }
    }
}

