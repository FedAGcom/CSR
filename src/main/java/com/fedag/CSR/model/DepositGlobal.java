package com.fedag.CSR.model;


import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "deposit_global")
@NoArgsConstructor
@AllArgsConstructor
public class DepositGlobal {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "deposit_id", nullable = false)
    private Long global_id;

}
