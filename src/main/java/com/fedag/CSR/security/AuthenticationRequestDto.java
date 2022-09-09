package com.fedag.CSR.security;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationRequestDto {
    private String userName;
    private String password;
}
