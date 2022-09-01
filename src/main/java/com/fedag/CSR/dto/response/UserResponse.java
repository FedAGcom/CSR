package com.fedag.CSR.dto.response;

import com.fedag.CSR.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {

    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private Role role;
    private String steamLink;
    private int balanceId;
}