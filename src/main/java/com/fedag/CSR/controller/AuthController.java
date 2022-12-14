package com.fedag.CSR.controller;

import com.fedag.CSR.repository.UserRepository;
import com.fedag.CSR.security.SteamAuthRequestDto;
import com.fedag.CSR.security.jwt.JwtTokenProvider;
import com.fedag.CSR.service.UserAuth;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Контроллер безопасности.", description = "Работа с безопасностью.")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
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
    public ResponseEntity<?> getUrl(HttpServletRequest request, SteamAuthRequestDto dto) {
        try {
            Map<String, Object> userInfo = userAuth.saveSteamUser(request, dto);

            HttpCookie cookie = ResponseCookie.from("AuthorizationCSRApp", (String) userInfo.get("token"))
                    .path("/")
                    .build();

            return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .location(URI.create("http://csgofarm.online")).build();
        } catch (IOException e) {
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

    @Operation(summary = "Выход из учетной записи.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Выход выполнен",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
    })
    @PostMapping("/logout")
    @PreAuthorize("hasAnyAuthority('user', 'admin')")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }

//    @Operation(summary = "Получение данных из базы, об авторизированном пользователе через сервис steam, по steam_id")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Данные получены",
//                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
//            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
//                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
//    })
//    @GetMapping("/success_url")
//    public ResponseEntity<?> getUserDataAfterAuthentication(@RequestParam("steam_id") String steamId) {
//        Optional<User> user = userRepository.findBySteamId(steamId);
//        return ResponseEntity.ok().body(userAuth.getUserData(user, steamId));
//    }

//    @Operation(summary = "Регистрация")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Регистрация",
//                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
//            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
//                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
//    })
//    @PostMapping("/registration")
//    public ResponseEntity<?> saveUser(@RequestBody RegistrationRequest registrationRequest) {
//        return ResponseEntity.ok(userAuth.saveUser(registrationRequest));
//    }

//    @Operation(summary = "Ввод и проверка данных для аутентификации и авторизации пользователя.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Пользователь зашел в учетную запись.",
//                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
//            @ApiResponse(responseCode = "403", description = "Ошибка ввода данных.",
//                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)})
//    })

//    @PostMapping("/login")
//    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDto request) {
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
//            User user = userRepository.findByUserName(request.getUserName()).orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));
//            String token = jwtTokenProvider.createToken(request.getUserName(), user.getRole().name());
//            Map<Object, Object> response = new HashMap<>();
//            response.put("userName", request.getUserName());
//            response.put("token", token);
//            return ResponseEntity.ok(response);
//        } catch (AuthenticationException e) {
//            return new ResponseEntity<>("Неверная пара username/пароль.", HttpStatus.FORBIDDEN);
//        }
//    }
}

