package com.fedag.CSR.model;



import com.fedag.CSR.enums.DepositStatus;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
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

    @Column(name = "transaction_id", nullable = false)
    private Long transactionId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
