package com.fedag.CSR.service.impl;

import com.fedag.CSR.enums.Role;
import com.fedag.CSR.email.EmailService;
import com.fedag.CSR.model.User;
import com.fedag.CSR.repository.UserRepository;
import com.fedag.CSR.security.UserDetailsServiceImpl;
import com.fedag.CSR.security.jwt.JwtTokenProvider;
import com.fedag.CSR.security.security_exception.RegistrationRequest;
import com.fedag.CSR.service.UserAuth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserAuthImpl implements UserAuth {
    final AuthenticationManager authenticationManager;
    final UserRepository userRepository;
    final EmailService emailService;
    final JwtTokenProvider jwtTokenProvider;

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

        String link = "http://localhost:8080/api/v1/activate/confirm?token=" + token;
        emailService.send(registrationRequest.getEmail(), registrationRequest.getFirstName(), link);

        responseMap.put("error", false);
        responseMap.put("username", registrationRequest.getUserName());
        responseMap.put("message", "Account created successfully");
        responseMap.put("token", token);
        return responseMap;
    }
}
