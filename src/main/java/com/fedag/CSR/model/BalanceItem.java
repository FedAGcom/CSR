package com.fedag.CSR.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
/**
 * Entity class balance columns id, type, title, rare, quality, price, many-to-one-relationship with balance model by
 * key balance_id for testing code
 *  @author Kirill Soklakov
 *  @version 1.1
 */
@Entity
public class BalanceItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(name = "type")
//    id, type, title, rare, quality, price
    private String type;
    @NotNull
    @Column(name = "title")
    private String title;

    @NotNull
    @Column(name = "rare")
    private String rare;

    @NotNull
    @Column(name = "quality")
    private String quality;

    @NotNull
    @Column(name = "price")
    private int price;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "balance_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Balance balance;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRare() {
        return rare;
    }

    public void setRare(String rare) {
        this.rare = rare;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }
}