package com.fedag.CSR.dto.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class PackUpdate {
    private String title;
    private BigDecimal price;
}
