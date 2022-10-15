package com.fedag.CSR.model;

import com.fedag.CSR.enums.ItemsWonStatus;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Table(name = "items_won")
@Getter
@Setter
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
    @JoinColumn(name = "balance_id")
    private Balance balances;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item items;

    @Column(name = "pack_price")
    private Double pack_price;

    @Column(name = "item_price")
    private Double item_price;

    @Column(name = "pack_opening_timestamp")
    private LocalDateTime pack_opening_timestamp;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_won_status", nullable = false)
    private ItemsWonStatus itemsWonStatus;

    @Column(name = "item_won_sailed_time")
    private LocalDateTime itemWonSailedTime;
}
