package com.fedag.CSR.dto.response;


import com.fedag.CSR.enums.CSRRole;
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
    private CSRRole role;
    private LocalDateTime created;
    private String steamLink;
    private String steam_id;
}