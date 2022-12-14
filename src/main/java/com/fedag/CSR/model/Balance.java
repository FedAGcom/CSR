package com.fedag.CSR.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class balance columns id, coins one-to-many relationship with item model by key balance_id
 *
 * @author Kirill Soklakov
 * @version 1.1
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Balance {

    public Balance(Double coins, User user) {
        this.coins = coins;
        this.user = user;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigDecimal id;

    @Column(name = "coins")
    private Double coins;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "balances")
    public List<ItemsWon> itemsWon;

}