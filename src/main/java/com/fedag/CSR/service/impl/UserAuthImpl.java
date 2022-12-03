package com.fedag.CSR.service.impl;

import com.fedag.CSR.enums.Role;
import com.fedag.CSR.email.EmailService;
import com.fedag.CSR.exception.EntityNotFoundException;
import com.fedag.CSR.model.Balance;
import com.fedag.CSR.model.User;
import com.fedag.CSR.repository.UserRepository;
import com.fedag.CSR.security.SteamAuthRequestDto;
import com.fedag.CSR.security.UserDetailsServiceImpl;
import com.fedag.CSR.security.jwt.JwtTokenProvider;
import com.fedag.CSR.security.security_exception.RegistrationRequest;
import com.fedag.CSR.service.UserAuth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserAuthImpl implements UserAuth {
    final AuthenticationManager authenticationManager;
    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;
    final EmailService emailService;
    final JwtTokenProvider jwtTokenProvider;
    @Value("${steam.apikey}")
    private String steamApiKey;
    @Value("${users.logged.via.steam.shared.password}")
    private String passwordLoggedViaSteam;

    @Override
    public Map<String, Object> saveUser(RegistrationRequest registrationRequest) {
        Map<String, Object> responseMap = new HashMap<>();
        User user = new User();
        user.setFirstName(registrationRequest.getFirstName());
        user.setLastName(registrationRequest.getLastName());
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(registrationRequest.getPassword()));
        user.setRole(Role.user);
        user.setUserName(registrationRequest.getUserName());
        user.setSteamLink(registrationRequest.getSteamLink());
        LocalDateTime now = LocalDateTime.now();
        user.setCreated(now);
        user.setEnabled(false);

        String token = jwtTokenProvider.createToken(user.getUserName(), user.getRole().name());
        user.setConfirmationToken(token);
        userRepository.save(user);

        String link = "http://csgofarm.online/api/v1/activate/confirm?token=" + token;
//        String link = "http://localhost:80/api/v1/activate/confirm?token=" + token;
        emailService.send(registrationRequest.getEmail(), registrationRequest.getFirstName(), link);

        responseMap.put("error", false);
        responseMap.put("username", registrationRequest.getUserName());
        responseMap.put("message", "Account created successfully");
        responseMap.put("token", token);
        return responseMap;
    }

    @Override
    public Map<String, Object> saveSteamUser(HttpServletRequest request, SteamAuthRequestDto dto) throws IOException {
        log.info("Получение данных с аккаунта Steam");
        Map<String, Object> responseMap = new HashMap<>();
        User user = new User();

        String queryString = request.getQueryString();

        String[] urlArray = queryString.split("%");
        String stringContainingId = urlArray[16];
        String[] sixteenthCellArray = stringContainingId.split("&");

        String result = sixteenthCellArray[0].substring(2);

        String stringUrl = ("https://api.steampowered.com/ISteamUser/GetPlayerSummaries/v0002/?key="
                + steamApiKey + "&steamids=" + result);

        URL url = new URL(stringUrl);
        String json = IOUtils.toString(url, StandardCharsets.UTF_8);
        JSONObject jsonObject = new JSONObject(json);
        JSONObject jsonObject1 = (JSONObject) jsonObject.get("response");
        JSONArray jsonArray = (JSONArray) jsonObject1.get("players");
        JSONObject jsonObject2 = (JSONObject) jsonArray.get(0);
        String profileurl = (String) jsonObject2.get("profileurl");
        String steamid = (String) jsonObject2.get("steamid");
        String personaname = (String) jsonObject2.get("personaname");
        String avatar = (String) jsonObject2.get("avatar");
        String avatarfull = (String) jsonObject2.get("avatarfull");
        String avatarmedium = (String) jsonObject2.get("avatarmedium");

        if (userRepository.findByUserName(personaname).isEmpty()) {
            user.setSteamLink(profileurl);
            user.setSteamId(steamid);
            user.setUserName(personaname);
            user.setSteamAvatarLink(avatar);
            user.setSteamAvatarMediumLink(avatarmedium);
            user.setSteamFullAvatarLink(avatarfull);
            LocalDateTime now = LocalDateTime.now();
            user.setCreated(now);
            user.setRole(Role.user);
            user.setEnabled(true);

            String password = String.valueOf((personaname + steamid).hashCode());
            user.setPassword(passwordEncoder.encode(password));

            user.setEmail("fedag@gmail.com");

            String token = jwtTokenProvider.createToken(user.getSteamId(), user.getRole().name());
            user.setConfirmationToken(token);
            userRepository.save(user);

            Optional<User> optional = userRepository.findUserByConfirmationToken(token);
            User userForBalance = optional.get();
            userForBalance.setBalance(new Balance(0.0, user));
            userRepository.save(userForBalance);

            responseMap.put("error", false);
            responseMap.put("username", personaname);
            responseMap.put("message", "Account created successfully");
            responseMap.put("token", token);
            log.info("Пользователь со Steam аккаунтом зарегистрирован");
            return responseMap;
        } else {
            String token = userRepository.findByUserName(personaname).get().getConfirmationToken();
            String userName = userRepository.findByUserName(personaname).get().getUserName();
            if (jwtTokenProvider.validateToken(token)) {
                Map<String, Object> response = new HashMap<>();
                response.put("userName", userName);
                response.put("token", token);
                return response;
            } else if (!jwtTokenProvider.validateToken(token)) {
                token = jwtTokenProvider.createToken(user.getSteamId(), user.getRole().name());
                user.setConfirmationToken(token);
                userRepository.save(user);
                Map<String, Object> response = new HashMap<>();
                response.put("userName", userName);
                response.put("token", token);
                return response;
            } else {
                responseMap.put("Error", true);
                responseMap.put("message", "Invalid token");
                return responseMap;
            }
        }
    }

    @Override
    public Map<String, Object> getUserData(Optional<User> optionalUser, String steamId) {
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Map<String, Object> responseMap = new LinkedHashMap<>();
            responseMap.put("error", false);
            responseMap.put("id", user.getId());
            responseMap.put("user_name", user.getUserName());
            responseMap.put("confirmation_token", user.getConfirmationToken());
            responseMap.put("steam_avatar", user.getSteamAvatarLink());
            responseMap.put("steam_medium", user.getSteamAvatarMediumLink());
            responseMap.put("steam_avatar_full", user.getSteamFullAvatarLink());
            responseMap.put("steam_link", user.getSteamLink());
            return responseMap;
        } else {
            throw new EntityNotFoundException("User", "SteamId", steamId);
        }
    }
}
