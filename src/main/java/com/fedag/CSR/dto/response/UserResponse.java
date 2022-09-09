package com.fedag.CSR.dto.response;


import com.fedag.CSR.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private BigDecimal id;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
    private LocalDateTime created;
    private String steamLink;
    private String userName;
}