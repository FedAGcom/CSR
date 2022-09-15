package com.fedag.CSR.service;

import com.fedag.CSR.security.SteamAuthRequestDto;
import com.fedag.CSR.security.security_exception.RegistrationRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public interface UserAuth {
    Map<String, Object> saveUser(RegistrationRequest request);
    Map<String, Object> saveSteamUser(HttpServletRequest request, SteamAuthRequestDto dto) throws IOException;
}
