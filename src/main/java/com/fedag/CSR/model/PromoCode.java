package com.fedag.CSR.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "promo_code")
public class PromoCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private BigDecimal id;

    @Column(name = "promo_code_name")
    private String promoCodeName;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "user_id")
    private BigDecimal userId;

    @Column(name ="expire_date")
    private LocalDate expireDate;
    @Column(name = "coins")
    private Double coins;

    @Override
    public String toString() {
        return "PromoCode{" +
                "id=" + id +
                ", promoCodeName='" + promoCodeName + '\'' +
                ", amount=" + amount +
                ", userId=" + userId +
                ", coins=" + coins +
                '}';
    }
}
