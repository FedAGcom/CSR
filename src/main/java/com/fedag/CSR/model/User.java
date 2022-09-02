package com.fedag.CSR.model;

import com.fedag.CSR.enums.Role;
import com.fedag.CSR.service.utils.PostgreSQLEnumType;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@TypeDef(
        name = "role",
        typeClass = PostgreSQLEnumType.class
)
@ApiModel(description = "The details about the user.")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    @Type(type = "role" )
    private Role role;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "steam_link")
    private String steamLink;

    @Column(name = "balance_id")
    private int balanceId;

}
