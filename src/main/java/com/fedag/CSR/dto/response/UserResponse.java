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
    //    private String email;
    private Double balance;
    private Role role;
    private String steamLink;
    private String userName;
    private String steamAvatarMedium;
    private String tradeUrl;
}