package com.fedag.CSR.dto.request;

import com.fedag.CSR.enums.CSRRole;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class UserRequest {

    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private CSRRole role;
    private String steamLink;
    private String steam_id;
}
