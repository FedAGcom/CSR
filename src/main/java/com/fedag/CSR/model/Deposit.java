package com.fedag.CSR.model;



import com.fedag.CSR.enums.DepositStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "deposits")
@NoArgsConstructor
@AllArgsConstructor
public class Deposit {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "deposit", nullable = false)
    private Long deposit;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private DepositStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
