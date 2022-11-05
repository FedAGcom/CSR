package com.fedag.CSR.service.impl;

import com.fedag.CSR.email.EmailService;
import com.fedag.CSR.enums.Role;
import com.fedag.CSR.exception.EntityNotFoundException;
import com.fedag.CSR.model.User;
import com.fedag.CSR.repository.UserRepository;
import com.fedag.CSR.security.SteamAuthRequestDto;
import com.fedag.CSR.security.jwt.JwtUtils;
import com.fedag.CSR.service.UserAuth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserAuthImpl implements UserAuth {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final JwtUtils jwtUtils;

    @Value("${steam.apikey}")
    private String steamApiKey;

    @Override
    public User saveSteamUser(HttpServletRequest request, SteamAuthRequestDto dto) throws IOException {
        log.info("Получение данных с аккаунта Steam");
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

        if (userRepository.findBySteamId(steamid).isEmpty()) {
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

            // TODO поменять логику
            user.setEmail("fedag@gmail.com");

            String token = jwtUtils.generateTokenFromUsername(personaname);
            user.setConfirmationToken(token);
            return userRepository.save(user);
        } else {
            Optional<User> userOptional = userRepository.findBySteamId(steamid);
            if (userOptional.isPresent()) {
                User userPresent = userOptional.get();
                if (!jwtUtils.validateJwtToken(userPresent.getConfirmationToken())) {
                    String token = jwtUtils.generateTokenFromUsername(personaname);
                    user.setConfirmationToken(token);
                    return userRepository.save(user);
                }
                else {
                    return userOptional.get();
                }
            } else throw new EntityNotFoundException("User", "steam_id", steamid);
        }
    }
}
