package com.fedag.CSR.dto.request;

import com.fedag.CSR.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserRequest {

    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private Role role;
    private String steamLink;
    private int balanceId;
}
