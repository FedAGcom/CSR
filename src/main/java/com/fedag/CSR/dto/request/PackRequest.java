package com.fedag.CSR.dto.request;

import com.fedag.CSR.model.Item;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class PackRequest {
    BigDecimal packId;
    private String title;
    private BigDecimal price;
    List<Item> items;
}