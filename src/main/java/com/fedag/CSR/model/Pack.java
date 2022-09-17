package com.fedag.CSR.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pack")
public class Pack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private BigDecimal id;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private BigDecimal price;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pack")
//    private List<Item> items;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pack")
    private List<WinChance> winChances;



}