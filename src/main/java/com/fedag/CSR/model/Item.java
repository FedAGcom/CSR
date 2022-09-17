package com.fedag.CSR.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

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
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "balance_id")
    private Balance balance;

//    @ManyToOne
//    @JoinColumn(name = "pack_id")
//    private Pack pack;

//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "pack_item"
//            , joinColumns = @JoinColumn(name = "item_id")
//            , inverseJoinColumns = @JoinColumn(name = "pack_id")
//    )
//    private List<Pack> packs;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "item")
    private List<WinChance> winChances;
}

