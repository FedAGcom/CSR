package com.fedag.CSR.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pack_item")
@IdClass(WinChanceId.class)
public class WinChance {

    @Id
    @Column(name = "pack_id", insertable = false, updatable = false)
    private Long packId;

    @Id
    @Column(name = "item_id", insertable = false, updatable = false)
    private Long itemId;

    @Column(name = "win_chance", updatable = false)
    private Double winChance;
}
