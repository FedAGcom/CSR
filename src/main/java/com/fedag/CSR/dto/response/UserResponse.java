package com.fedag.CSR.dto.response;


import com.fedag.CSR.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserResponse {

    private Role role;
    private String steamLink;
    private String userName;
    private String steamAvatarMedium;
}