package com.fedag.CSR.dto.response;

import com.fedag.CSR.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserResponse {

    private int id;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
    private LocalDateTime created;
    private String steamLink;
    private int balanceId;
}