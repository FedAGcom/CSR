package com.fedag.CSR.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity class balance columns id, coins one-to-many relationship with item model by key balance_id
 *  @author Kirill Soklakov
 *  @version 1.1
 */
@Data
@Entity
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int coins;

    @OneToMany(mappedBy = "balance", cascade = CascadeType.ALL)
    private List<BalanceItem> balanceItems = new ArrayList<>();
}