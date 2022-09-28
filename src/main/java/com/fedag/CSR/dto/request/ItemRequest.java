package com.fedag.CSR.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ItemRequest {

    private BigDecimal id;

    private String type;

    private String title;

    private String rare;

    private String quality;

    private double price;

    private BigDecimal balanceId;

    private BigDecimal pack_id;

    private String steam_id;
}
