package com.fedag.CSR.service;

import com.fedag.CSR.security.security_exception.RegistrationRequest;

import java.util.Map;

public interface UserAuth {
    Map<String, Object> saveUser(RegistrationRequest request);
}
