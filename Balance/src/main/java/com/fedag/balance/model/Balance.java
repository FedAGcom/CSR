package com.fedag.balance.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
//Entity class balance колонки id, coins связь один ко многи с item model по ключу balance_id, c геттерами и сеттерами
@Entity
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private int coins;

    @OneToMany(mappedBy = "balance", cascade = CascadeType.ALL)
    private Set<Item> items = new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;

        for(Item b : items) {
            b.setBalance(this);
        }
    }
}