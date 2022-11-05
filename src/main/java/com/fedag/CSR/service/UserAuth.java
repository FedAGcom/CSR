package com.fedag.CSR.service;

import com.fedag.CSR.model.User;
import com.fedag.CSR.security.SteamAuthRequestDto;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface UserAuth {
    User saveSteamUser(HttpServletRequest request, SteamAuthRequestDto dto) throws IOException;
}
