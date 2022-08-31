package com.fedag.CSR.models;

import com.fedag.CSR.services.utils.PostgreSQLEnumType;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@TypeDef(
        name = "role",
        typeClass = PostgreSQLEnumType.class
)
@ApiModel(description = "The details about the user.")
public class User {

    public static enum Role {user, admin}
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
    @Type( type = "role" )
    private Role role;

    @Column(name = "created")
    private LocalDateTime created;

    @Column(name = "steam_link")
    private String steamLink;

    @Column(name = "balance_id")
    private int balanceId;

    public User() {
    }

    public User(String email, String firstName, String lastName, String password, Role role, LocalDateTime created, String steamLink, int balanceId) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.role = role;
        this.created = created;
        this.steamLink = steamLink;
        this.balanceId = balanceId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getSteamLink() {
        return steamLink;
    }

    public void setSteamLink(String steamLink) {
        this.steamLink = steamLink;
    }

    public int getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(int balanceId) {
        this.balanceId = balanceId;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", created=" + created +
                ", steamLink='" + steamLink + '\'' +
                ", balanceId=" + balanceId +
                '}';
    }
}
