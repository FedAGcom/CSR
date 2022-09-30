package com.fedag.CSR.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "items_won")
public class ItemsWon {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User users;

    @ManyToOne
    @JoinColumn(name = "pack_id")
    private Pack packs;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item items;

    @Column(name = "pack_price")
    private Double pack_price;

    @Column(name = "item_price")
    private Double item_price;

    @Column(name = "pack_opening_timestamp")
    private LocalDateTime pack_opening_timestamp;

}
