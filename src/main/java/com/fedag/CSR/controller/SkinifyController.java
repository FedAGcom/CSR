package com.fedag.CSR.controller;

import com.fedag.CSR.enums.DepositStatus;
import com.fedag.CSR.service.impl.SkinifyServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SkinifyController {
    private final SkinifyServiceImpl skinifyService;

    @Value("${skinify.token.md5}")
    String skinifyTokenMD5;


    @PostMapping("/create-deposit")
    @PreAuthorize("hasAuthority('user')")
    public ResponseEntity<?> createDepositPost(@RequestHeader("Authorization") String userToken) {
        String url = skinifyService.createDeposit(userToken);

        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(url)).build();
    }

    @GetMapping("/result")
    public ResponseEntity<?> showResult(HttpServletRequest servletRequest) {

        if (servletRequest.getParameter("token_md5").equals(skinifyTokenMD5)) {
            if (servletRequest.getParameter("status").equals("success")) {
                String depositId = servletRequest.getParameter("deposit_id");
                String steamId = servletRequest.getParameter("steam_id");
                String amount = servletRequest.getParameter("amount");
                return ResponseEntity.ok().body(skinifyService.checkSuccessStatus(depositId, steamId, amount));
            } else if (servletRequest.getParameter("status").equals("failed")) {
                String depositId = servletRequest.getParameter("deposit_id");
                return ResponseEntity.ok().body(skinifyService.checkFailedStatus(depositId));
            }

        }
        return null;
    }
    @GetMapping("/success")
    public ResponseEntity<?> showSuccessUrl(){
        return ResponseEntity.ok().body("Оплата успешно произведена");
    }

    @GetMapping("/fail")
    public ResponseEntity<?> showFailedUrl(){
        return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.BAD_REQUEST);
    }
}


//    @GetMapping("/check-status/{depositId}")
////    @PreAuthorize("hasAnyAuthority())
//    public ResponseEntity<DepositStatus> checkDepositStatus(@PathVariable Long depositId,
//                                                            @RequestHeader("Authorization") String userToken) throws InterruptedException {
//        DepositStatus depositStatus = skinifyService.checkDepositStatus(depositId, userToken);
//        while (depositStatus != DepositStatus.SUCCESS && depositStatus != DepositStatus.ERROR) {
//            Thread.sleep(3000);
//            depositStatus = skinifyService.checkDepositStatus(depositId, userToken);
//            System.out.println(depositStatus);
//        }
//        return ResponseEntity.ok().body(depositStatus);
//    }