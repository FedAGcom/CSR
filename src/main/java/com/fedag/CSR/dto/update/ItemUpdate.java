package com.fedag.CSR.dto.update;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ItemUpdate {

    private BigDecimal id;

    private String type;

    private String title;

    private String rare;

    private String quality;

    private double price;

    private BigDecimal balanceId;

    private BigDecimal packId;

    private String steam_id;
}


