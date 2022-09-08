package com.fedag.CSR.dto.update;


import com.fedag.CSR.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserUpdate {

    private BigDecimal id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private Role role;
    private LocalDateTime created;
    private String steamLink;
    private String steam_id;
}
