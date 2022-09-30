package com.fedag.CSR.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigDecimal itemId;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "rare", nullable = false)
    private String rare;

    @Column(name = "quality", nullable = false)
    private String quality;

    @Column(name = "price", nullable = false)
    private Double price;

    @ManyToOne
    @JoinColumn(name = "pack_id")
    private Pack pack;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "items")
    public List<ItemsWon> itemsWon;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "item")
    public List<WinChance> winChances;
}

