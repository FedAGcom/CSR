package com.fedag.CSR.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PackResponse {
    private String title;
    private BigDecimal price;
    private List<BigDecimal> packItemsId;
}

