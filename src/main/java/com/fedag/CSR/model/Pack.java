package com.fedag.CSR.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fedag.CSR.enums.DepositStatus;
import com.fedag.CSR.enums.PackStatus;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pack")
public class Pack implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private BigDecimal id;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private Double price;

    @Column(name = "pack_image")
    private String image;

    @Column(name = "image_type")
    private String imageType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PackStatus status;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pack")
    private List<Item> items;

    @OneToMany(cascade = CascadeType.ALL,  mappedBy = "packs")
    public List<ItemsWon> itemsWon;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pack")
    private List<WinChance> winChances;
}