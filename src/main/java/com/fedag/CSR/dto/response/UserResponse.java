package com.fedag.CSR.dto.response;


import com.fedag.CSR.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserResponse {

    private BigDecimal id;
    private Role role;
    private String userName;
    private Double balance;
    //    private String steamLink;
    private String steamAvatarMedium;
    private String email;
    private String tradeUrl;
}