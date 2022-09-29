package com.fedag.CSR.model;


import com.fedag.CSR.enums.Role;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
    private BigDecimal id;

    @Column(name = "email")
    private String email;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    @Type(type = "role")
    private Role role;

    @Column(name = "created", updatable = false)
    private LocalDateTime created;

    @Column(name = "steam_link")
    private String steamLink;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "confirmation_token")
    private String confirmationToken;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "steam_id")
    private String steamId;

    @Column(name = "steam_avatar")
    private String steamAvatarLink;

    @Column(name = "steam_avatar_medium")
    private String steamAvatarMediumLink;

    @Column(name = "steam_avatar_full")
    private String steamFullAvatarLink;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private Balance balance;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    List<Deposit> deposits;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
    public List<ItemsWon> itemsWon;
}
