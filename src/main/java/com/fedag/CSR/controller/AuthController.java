package com.fedag.CSR.controller;

import com.fedag.CSR.model.User;
import com.fedag.CSR.security.SteamAuthRequestDto;
import com.fedag.CSR.security.jwt.JwtUtils;
import com.fedag.CSR.security.service.UserDetailsImpl;
import com.fedag.CSR.service.UserAuth;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Контроллер безопасности.", description = "Работа с безопасностью.")
public class AuthController {

    private final UserAuth userAuth;
    @Value("${steam.openid.ns}")
    private String steamOpenIdNs;
    @Value("${steam.openid.claimed_id}")
    private String steamOpenIdClaimedId;
    @Value("${steam.openid.identity}")
    private String steamOpenIdIdentity;
    @Value("${steam.openid.return_to}")
    private String steamOpenIdReturnTo;
    @Value("${steam.openid.realm}")
    private String steamOpenIdRealm;
    @Value("${steam.openid.mode}")
    private String steamOpenIdMode;
    private final JwtUtils jwtUtils;


    @Operation(summary = "Регистрация через Steam")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Регистрация",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @GetMapping("/steam-registration")
    public ResponseEntity<?> saveUserWithSteam() {
        Map<String, String> hashMap = new HashMap<>();
        String link = "https://steamcommunity.com/openid/login?openid.ns=" + steamOpenIdNs +
                "&openid.claimed_id=" + steamOpenIdClaimedId +
                "&openid.identity=" + steamOpenIdIdentity +
                "&openid.return_to=" + steamOpenIdReturnTo +
                "&openid.realm=" + steamOpenIdRealm +
                "&openid.mode=" + steamOpenIdMode;
        hashMap.put("link", link);
        return ResponseEntity.ok().body(hashMap);
    }

    @Operation(summary = "Направляемый запрос для сохранения данных")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные о пользователе со steam сохранены",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @GetMapping("/authentication-with-steam")
    public ResponseEntity<?> getUrl(HttpServletRequest request, SteamAuthRequestDto dto){
        try {
            User user = userAuth.saveSteamUser(request, dto);

            UserDetailsImpl userDetails = new UserDetailsImpl(user.getId(), user.getUserName(),
                    user.getSteamId());
            ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

            return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .location(URI.create("http://localhost:8080/")).build();
        }
        catch (IOException e) {
            return ResponseEntity.status(HttpStatus.FOUND).location(URI
                    .create("http://csgofarm.online/api/v1/auth/error_url")).build();
        }
    }


    @Operation(summary = "Ошибка получения данных с сервиса steam")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Данные не найдены",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @GetMapping("/error_url")
    public void getErrorAfterFailed() {
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Данные аккаунта steam не найдены");
    }
}