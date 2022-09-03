package com.fedag.CSR.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponse {
    private BigDecimal id;

    private String type;

    private String title;

    private String rare;

    private String quality;

    private double price;

}
